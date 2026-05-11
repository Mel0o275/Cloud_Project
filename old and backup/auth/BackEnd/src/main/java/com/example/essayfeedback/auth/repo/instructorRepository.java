package com.example.essayfeedback.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.essayfeedback.auth.entity.Instructor;

@Repository
public interface instructorRepository extends JpaRepository<Instructor, Long> {
    // Optional<User> findByEmail(String Email);
}