package com.example.essayfeedback.auth.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.essayfeedback.auth.service.AuthService;
import com.example.essayfeedback.auth.service.CloudinaryService;
import com.example.essayfeedback.auth.entity.ApiResponse;
import com.example.essayfeedback.auth.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody User user){
        return authService.login(user);
    }

    // consumes = {"multipart/form-data"}
    // consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> signup(@Valid @RequestPart("user") User user ,@RequestPart(value = "files", required = false) List<MultipartFile> files){
        try {
            if(files == null){
                files = new ArrayList<>();
            }
        }
        catch (Exception e) {
            files = new ArrayList<>();
        }
        return authService.signup(user, files);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletRequest requesttBody){
        return authService.logout(requesttBody);
    }


// @GetMapping("/admin/doctors/{id}")
// public instructor getDoctor(@PathVariable Long id) {
//     return cloudinaryService.getDoctor(id);
// }

}
