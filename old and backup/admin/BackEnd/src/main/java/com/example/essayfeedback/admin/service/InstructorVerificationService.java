package com.example.essayfeedback.admin.service;

import com.example.essayfeedback.admin.exception.InstructorNotFoundException;
import com.example.essayfeedback.admin.dto.InstructorPendingDto;
import com.example.essayfeedback.admin.dto.InstructorVerificationDecisionDto;
import com.example.essayfeedback.admin.repo.AdminInstructorRepository;
import com.example.essayfeedback.admin.repo.AdminUserRepository;
// import com.example.essayfeedback.auth.entity.Instructor;
// import com.example.essayfeedback.auth.entity.User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import com.example.essayfeedback.admin.entity.Instructor;
import com.example.essayfeedback.admin.entity.User;

@Service
public class InstructorVerificationService {

    private final AdminUserRepository userRepository;
    private final AdminInstructorRepository instructorRepository;

    public InstructorVerificationService(AdminUserRepository userRepository, AdminInstructorRepository instructorRepository) {
        this.userRepository = userRepository;
        this.instructorRepository = instructorRepository;
    }

    public List<InstructorPendingDto> getPendingInstructors() {
        return userRepository.findByRoleIgnoreCaseAndAuth("INSTRUCTOR", false)
                .stream()
                .map(user -> {
                    Instructor instructor = instructorRepository.findByUser(user).orElse(null);
                    return new InstructorPendingDto(user, instructor);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public String verifyInstructor(Long id, InstructorVerificationDecisionDto decision) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new InstructorNotFoundException(id));

        if (decision.getIsAccepted()) {
            user.authtrue();
            userRepository.save(user);
            return "Instructor " + user.getUsername() + " has been accepted.";
        } else {
            instructorRepository.findByUser(user).ifPresent(instructorRepository::delete);
            userRepository.delete(user);
            return "Instructor " + user.getUsername() + " has been rejected and removed.";
        }
    }
}
