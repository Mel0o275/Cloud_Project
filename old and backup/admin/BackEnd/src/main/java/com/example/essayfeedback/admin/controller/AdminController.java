package com.example.essayfeedback.admin.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.essayfeedback.admin.dto.UserDto;
import com.example.essayfeedback.admin.service.AdminService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin/users")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok("User with id " + id + " has been deleted.");
    }

    @PutMapping("/{id}/freeze")
    public ResponseEntity<?> freezeUser(@PathVariable Long id) {
        UserDto frozenUser = adminService.freezeUser(id);
        return ResponseEntity.ok(frozenUser);
    }

    @PutMapping("/{id}/unfreeze")
    public ResponseEntity<?> unfreezeUser(@PathVariable Long id) {
        UserDto unfrozenUser = adminService.unfreezeUser(id);
        return ResponseEntity.ok(unfrozenUser);
    }
}
