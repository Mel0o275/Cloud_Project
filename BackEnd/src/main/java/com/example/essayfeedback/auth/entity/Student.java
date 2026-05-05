package com.example.essayfeedback.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student {
    @Id
    private long id;
    private String essay;
    private float score;

    @OneToOne
    private User user;

    // Manual Getters
    public long getId() {
        return id;
    }

    public String getEssay() {
        return essay;
    }

    public float getScore() {
        return score;
    }

    public User getUser() {
        return user;
    }

    // Manual Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setEssay(String essay) {
        this.essay = essay;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
