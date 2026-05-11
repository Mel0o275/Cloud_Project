package com.example.essayfeedback.admin.dto;

// import com.example.essayfeedback.auth.entity.Instructor;
// import com.example.essayfeedback.auth.entity.User;
import java.util.List;

// import com.example.essayfeedback.admin.entity.Instructor;
import com.example.essayfeedback.auth.entity.Instructor;
import com.example.essayfeedback.admin.entity.User;

import lombok.Data;

@Data
public class InstructorPendingDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    
    // Instructor profile info
    private List<String> certificates;

    public InstructorPendingDto(User user, Instructor instructor) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        
        if (instructor != null) {
            this.certificates = instructor.getCertificates();
        }
    }
}
