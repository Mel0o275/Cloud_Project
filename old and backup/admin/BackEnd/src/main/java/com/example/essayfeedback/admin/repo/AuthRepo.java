// package com.example.essayfeedback.auth.repo;
package com.example.essayfeedback.admin.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.essayfeedback.admin.entity.User;

// import com.example.essayfeedback.auth.entity.User;
@Repository
public interface AuthRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String Email);
}