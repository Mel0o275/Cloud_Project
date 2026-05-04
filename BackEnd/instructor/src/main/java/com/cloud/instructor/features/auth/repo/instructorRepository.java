package com.cloud.instructor.features.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloud.instructor.features.auth.entity.instructor;
@Repository
public interface instructorRepository extends JpaRepository<instructor, Long> {
    // Optional<User> findByEmail(String Email);
}