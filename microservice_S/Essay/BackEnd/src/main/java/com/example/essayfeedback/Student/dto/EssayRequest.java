package com.example.essayfeedback.Student.dto;

public class EssayRequest {
    private String content;
    private Long studentId;
    private String studentUsername;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentUsername() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}