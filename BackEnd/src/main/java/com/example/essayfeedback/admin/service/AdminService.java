package com.example.essayfeedback.admin.service;

import com.example.essayfeedback.admin.exception.AdminUserNotFoundException;
import com.example.essayfeedback.admin.dto.UserDto;
import com.example.essayfeedback.admin.repo.AdminUserRepository;
import com.example.essayfeedback.admin.repo.AdminInstructorRepository;
import com.example.essayfeedback.auth.repo.studentRepo;
import com.example.essayfeedback.student.repository.EssayRepository;
import com.example.essayfeedback.auth.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final AdminUserRepository userRepository;
    private final AdminInstructorRepository instructorRepository;
    private final studentRepo studentRepository;
    private final EssayRepository essayRepository;

    public AdminService(AdminUserRepository userRepository, 
                        AdminInstructorRepository instructorRepository,
                        studentRepo studentRepository,
                        EssayRepository essayRepository) {
        this.userRepository = userRepository;
        this.instructorRepository = instructorRepository;
        this.studentRepository = studentRepository;
        this.essayRepository = essayRepository;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(user -> !user.getRole().equalsIgnoreCase("admin"))
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getRole().equalsIgnoreCase("student")) {
                essayRepository.deleteByStudentId(id);
                studentRepository.findById(id).ifPresent(studentRepository::delete);
            } else if (user.getRole().equalsIgnoreCase("instructor")) {
                instructorRepository.findById(id).ifPresent(instructorRepository::delete);
            }
            userRepository.delete(user);
        }
    }

    @Transactional
    public UserDto freezeUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFrozen(true);
            userRepository.save(user);
            return new UserDto(user);
        }
        throw new AdminUserNotFoundException(id);
    }

    @Transactional
    public UserDto unfreezeUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFrozen(false);
            userRepository.save(user);
            return new UserDto(user);
        }
        throw new AdminUserNotFoundException(id);
    }
}
