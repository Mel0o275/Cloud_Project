package com.cloud.instructor.features.InstructorFeatures.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.instructor.features.InstructorFeatures.entity.ApiResponse;
import com.cloud.instructor.features.InstructorFeatures.service.instructorService;


@RestController 
public class instructorController {
    @Autowired
    private instructorService instructorService;
    @GetMapping("/instructors")
    public ResponseEntity<ApiResponse> listAll() {
        return instructorService.listAll();
    }

    @GetMapping("/instructors/{id}")
    public ResponseEntity<ApiResponse> get(@PathVariable long id) 
    {
        return instructorService.get(id);
    }
    
}
