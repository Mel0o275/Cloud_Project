package com.example.essayfeedback.admin.repo;

import com.example.essayfeedback.auth.entity.Instructor;
import com.example.essayfeedback.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminInstructorRepository extends JpaRepository<Instructor, Long> {
    Optional<Instructor> findByUser(User user);
}
