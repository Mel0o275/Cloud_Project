package com.example.essayfeedback.admin.exception;

import org.springframework.http.HttpStatus;

public class InstructorNotFoundException extends AdminException {
    public InstructorNotFoundException(Long id) {
        super("Error: Instructor with ID " + id + " does not exist in the verification system.", HttpStatus.NOT_FOUND);
    }
}
