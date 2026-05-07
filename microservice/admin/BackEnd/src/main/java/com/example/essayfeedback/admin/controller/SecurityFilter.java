package com.example.essayfeedback.admin.controller;

// import com.example.essayfeedback.auth.entity.User;
// import com.example.essayfeedback.auth.repo.AuthRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Key;
import java.util.Optional;

import com.example.essayfeedback.admin.entity.User;
import com.example.essayfeedback.admin.repo.AuthRepo;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final AuthRepo authRepo;
    private final String SECRET = "my-secret-key-my-secret-key-my-secret-key";

    public SecurityFilter(AuthRepo authRepo) {
        this.authRepo = authRepo;
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String path = request.getRequestURI();
        String method = request.getMethod();
        
        // Public paths and CORS preflight
        if (method.equals("OPTIONS") || path.startsWith("/auth/") || path.equals("/") || path.endsWith(".html") || path.endsWith(".css") || path.endsWith(".js")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            if (path.startsWith("/api/")) {
                sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            String email = claims.getSubject();
            String role = claims.get("role", String.class);

            if (email == null || role == null) {
                sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token payload");
                return;
            }

            Optional<User> userOpt = authRepo.findByEmail(email);
            if (userOpt.isEmpty()) {
                sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                return;
            }

            User user = userOpt.get();
            if (user.isFrozen()) {
                sendError(response, HttpServletResponse.SC_FORBIDDEN, "Your account is frozen. Access denied.");
                return;
            }

            String normalizedRole = role.toLowerCase();
            
            // 1. Admin API Protection
            if (path.startsWith("/api/admin/") && !normalizedRole.contains("admin")) {
                sendError(response, HttpServletResponse.SC_FORBIDDEN, "Access denied: Admin role required");
                return;
            }

            // 2. Instructor API Protection
            if (path.startsWith("/api/instructor/") && !normalizedRole.contains("instructor")) {
                sendError(response, HttpServletResponse.SC_FORBIDDEN, "Access denied: Instructor role required");
                return;
            }

            // 3. Essay API Protection
            if (path.startsWith("/api/essays")) {
                // Listing all essays: Allowed for Instructor and Admin
                if (path.equals("/api/essays") && method.equals("GET")) {
                    if (!normalizedRole.contains("instructor") && !normalizedRole.contains("admin")) {
                        sendError(response, HttpServletResponse.SC_FORBIDDEN, "Access denied: Instructor or Admin role required to list all essays");
                        return;
                    }
                }
                
                // Submitting essays: Allowed for Students only
                if (path.contains("/submit") && !normalizedRole.contains("student")) {
                    sendError(response, HttpServletResponse.SC_FORBIDDEN, "Access denied: Only students can submit essays");
                    return;
                }

                // Student-specific endpoints: Allowed for Students, Instructors, and Admins
                // (Instructors and Admins might need to view a specific student's list)
                if (path.contains("/student/")) {
                    if (!normalizedRole.contains("student") && !normalizedRole.contains("instructor") && !normalizedRole.contains("admin")) {
                        sendError(response, HttpServletResponse.SC_FORBIDDEN, "Access denied: Authorized role required");
                        return;
                    }
                }
            }

        } catch (Exception e) {
            if (path.startsWith("/api/")) {
                sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
                return;
            }
        }
        
        filterChain.doFilter(request, response);
    }

    private void sendError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"success\": false, \"message\": \"%s\"}", message));
    }
}
