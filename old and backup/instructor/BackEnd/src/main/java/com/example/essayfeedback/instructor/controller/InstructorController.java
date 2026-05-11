package com.example.essayfeedback.instructor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.essayfeedback.instructor.entity.ApiResponse;
import com.example.essayfeedback.instructor.service.InstructorService;


@CrossOrigin(origins = "*")
@RestController 
public class InstructorController {
    @Autowired
    private InstructorService instructorService;

    @GetMapping("/api/instructor/essays")
    public ResponseEntity<ApiResponse> listAll() {
        return instructorService.listAll();
    }

    @GetMapping("/api/instructor/essays/{id}")
    public ResponseEntity<ApiResponse> get(@PathVariable long id) 
    {
        return instructorService.get(id);
    }
}
