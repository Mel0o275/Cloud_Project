// package com.example.essayfeedback.auth.repo;
package com.example.essayfeedback.admin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import com.example.essayfeedback.auth.entity.Student;

// import com.example.essayfeedback.auth.entity.User;

import java.util.Optional;

import com.example.essayfeedback.admin.entity.Student;
import com.example.essayfeedback.admin.entity.User;

@Repository
public interface studentRepo extends JpaRepository<Student, Long> {
    Optional<Student> findByUser(User user);
}