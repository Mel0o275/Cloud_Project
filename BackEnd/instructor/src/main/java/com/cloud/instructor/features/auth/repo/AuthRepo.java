package com.cloud.instructor.features.auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloud.instructor.features.auth.entity.User;
@Repository
public interface AuthRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String Email);
}