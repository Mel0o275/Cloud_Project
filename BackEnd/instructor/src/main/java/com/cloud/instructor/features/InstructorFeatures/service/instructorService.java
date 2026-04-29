package com.cloud.instructor.features.InstructorFeatures.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cloud.instructor.features.InstructorFeatures.Repository.instructorRepo;
import com.cloud.instructor.features.InstructorFeatures.entity.ApiResponse;
import com.cloud.instructor.features.InstructorFeatures.entity.student;

@Service
public class instructorService 
{
    @Autowired
    private instructorRepo repo;

    public ResponseEntity<ApiResponse> listAll()
    {
        // repo.save( new instructor());

        List<student> students = repo.findAll();
        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "No students found", null));
        }
        return ResponseEntity.ok(
            new ApiResponse(true, "Students found", students)
        );
    }
    public ResponseEntity<ApiResponse> get(long id)
    {
        if (repo.findById(id).orElse(null) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "Student not found", null));
        }
        return ResponseEntity.ok(
            new ApiResponse(true, "Student found", repo.findById(id))
        );
    }
}