package com.example.essayfeedback.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.essayfeedback.auth.entity.ApiResponse;
import com.example.essayfeedback.auth.entity.DataResponse;
// import com.example.essayfeedback.auth.entity.Student;
import com.example.essayfeedback.auth.entity.User;
import com.example.essayfeedback.auth.helper.JwtUtil;
import com.example.essayfeedback.auth.repo.AuthRepo;
// import com.example.essayfeedback.auth.repo.studentRepo;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService {
    @Autowired
    private AuthRepo authRepo;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CloudinaryService cloudinaryService;

    // @Autowired
    // private studentRepo studentRepo;



    public ResponseEntity<ApiResponse> login(User user){
        var dbUser = authRepo.findByEmail(user.getEmail());

if (dbUser.isPresent() &&
    BCrypt.checkpw(user.getPassword(), dbUser.get().getPassword()) && dbUser.get().getAuth() == true) {

    String token = jwtUtil.generateToken(user, dbUser);
    // tokenBlacklistService.blacklistToken(token, jwtUtil.getRemainingTime(token));

    return ResponseEntity.ok(
        new ApiResponse(
            true,
            "login success",
            token,
            null
        )
    );
}
        return ResponseEntity.ok(
            new ApiResponse(
                false,
                "login failed",
                new DataResponse(
                    user.getAuth(),
                    user.getUsername(),
                    user.getRole(),
                    user.getId()
                ),
                null
            )
        );
    }

    public ResponseEntity<ApiResponse> signup(User user ,List<MultipartFile> files){
        user.authfalse();
        if (authRepo.findByEmail(user.getEmail()).isPresent()){
            return ResponseEntity.ok(
                new ApiResponse(
                    false,
                    "Email already exist",
                    null,
                    null
                )
            );
        }
        if(!(user.getRole().contains("Student")) && !(user.getRole().contains("instructor"))){
            return ResponseEntity.ok(
                new ApiResponse(
                    false,
                    "invalid role",
                    null,
                    null
                )
            );
        }
        if(user.getRole().contains("Student")){
            user.authtrue();
        }

        user.hashpassword();
        user.encryptPhone();
        authRepo.save(user);
        // Student student = new Student();
        // student.setId(user.getId());
        // studentRepo.save(student);

        if(user.getRole().contains("instructor")){
            if(files.isEmpty()){
                return ResponseEntity.ok(
                    new ApiResponse(
                        false,
                        "upload certificates",
                        null,
                        null
                    )
                );
            }
            cloudinaryService.uploadCertificates(user.getId(), files);
        }

        return ResponseEntity.ok(
            new ApiResponse(
                true,
                "signup success",
                new DataResponse(
                    user.getAuth(),
                    user.getUsername(),
                    user.getRole(),
                    user.getId()
                ),
                null
            )
        );
    }

    public ResponseEntity<ApiResponse> logout(HttpServletRequest request){

        String token = extractToken(request);
        if(token == null || !jwtUtil.isValidToken(token)){
            return ResponseEntity.ok(
                new ApiResponse(
                    false,
                    "logout failed",
                    null,
                    null
                )
            );
        }

    long expiration = jwtUtil.getRemainingTime(token);

    tokenBlacklistService.blacklistToken(token, expiration);

        return ResponseEntity.ok(
            new ApiResponse(
                true,
                "logout success",
                null,
                null
            )
        );
    }

    private String extractToken(HttpServletRequest request) {
        if (request == null || request.getHeader("Authorization") == null) {
    throw new RuntimeException("JWT is missing !!");
}
    String bearerToken = request.getHeader("Authorization");

    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
        return bearerToken.substring(7); // يشيل "Bearer "
    }

    return null;
}

}

//     public ResponseEntity<ApiResponse> logout(HttpServletRequest request) {
//     String token = extractToken(request); // هقولك عليها تحت
//     // blacklistService.add(token);

//     return ResponseEntity.ok(
//         new ApiResponse(true, "logout success", token, null)
//     );
// }

// private String extractToken(HttpServletRequest request) {
//     String header = request.getHeader("Authorization");

//     if (header != null ) {
//         return header;
//     }
//     return null;
// }



    // private final String SECRET = "my-secret-key-my-secret-key-my-secret-key";

    // private Key getSignKey() {
    //     return Keys.hmacShaKeyFor(SECRET.getBytes());
    // }

//     public String extractUsername(String token) {
//     return Jwts.parser()
//             .verifyWith(getSignKey())
//             .build()
//             .parseSignedClaims(token)
//             .getPayload()
//             .getSubject();
// }

//     public String verifytoken(String token){
//         String username = extractUsername(token);
//         System.out.println(username);
//     }

////////////underwork//////////////////////////////////////////////////