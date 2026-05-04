package com.cloud.instructor.features.auth.entity;

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
}
