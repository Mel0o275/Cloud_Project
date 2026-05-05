package com.example.essayfeedback.student.entity;

import jakarta.persistence.*;
import jakarta.persistence.Transient;

@Entity
@Table(name = "essays")
public class Essay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "s3_key", nullable = false)
    private String s3Key;

    private Double score;
    private String feedback;

    @Column(name = "student_id")
    private Long studentId;

    @Transient
    private String studentUsername;

    public Essay() {}

    public Essay(String s3Key, Double score, String feedback, Long studentId) {
        this.s3Key = s3Key;
        this.score = score;
        this.feedback = feedback;
        this.studentId = studentId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getS3Key() {
        return s3Key;
    }

    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }
}