package com.cloud.instructor.features.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloud.instructor.features.auth.entity.Student;

@Repository
public interface studentRepo extends JpaRepository<Student, Long> {
    // Optional<User> findByEmail(String Email);
}