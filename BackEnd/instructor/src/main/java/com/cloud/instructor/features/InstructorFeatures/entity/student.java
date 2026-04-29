package com.cloud.instructor.features.InstructorFeatures.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
@Entity
@Getter
public class student{
    @Id
    @GeneratedValue
    private long id;

    private long score;

    private String essay;
}