package com.cloud.instructor.features.auth.entity;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Instructor {
    
    @Id
    private Long id;


    @ElementCollection
    private List<String> certificates; // URLs

    @OneToOne
    private User user;

}
