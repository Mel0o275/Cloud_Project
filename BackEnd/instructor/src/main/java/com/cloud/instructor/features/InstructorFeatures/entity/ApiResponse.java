package com.cloud.instructor.features.InstructorFeatures.entity;

import lombok.Getter;

@Getter
public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;
    // private LocalDateTime timestamp;

    // public ApiResponse() {
    //     this.timestamp = LocalDateTime.now();
    // }

    public ApiResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
        // this.timestamp = LocalDateTime.now();
    }
}