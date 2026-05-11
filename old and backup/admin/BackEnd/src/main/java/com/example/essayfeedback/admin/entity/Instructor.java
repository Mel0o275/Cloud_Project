// package com.example.essayfeedback.auth.entity;
package com.example.essayfeedback.admin.entity;

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

    // Manual Getters
    public Long getId() {
        return id;
    }

    public List<String> getCertificates() {
        return certificates;
    }

    public User getUser() {
        return user;
    }

    // Manual Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setCertificates(List<String> certificates) {
        this.certificates = certificates;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
