package com.example.essayfeedback.student.dto;

public class AiModelRequest {
    private String text;

    public AiModelRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}