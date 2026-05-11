package com.example.essayfeedback.instructor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

// import com.cloudinary.api.ApiResponse;

// import com.example.essayfeedback.student.entity.Essay;
// import com.example.essayfeedback.student.repository.EssayRepository;
// import com.example.essayfeedback.instructor.entity.ApiResponse;
// import com.example.essayfeedback.student.service.S3Service;
// import com.example.essayfeedback.student.service.S3Service;

import com.example.essayfeedback.Student.entity.Essay;
import com.example.essayfeedback.Student.repository.EssayRepository;
import com.example.essayfeedback.Student.service.S3Service;
import com.example.essayfeedback.instructor.entity.ApiResponse;



@Service
public class InstructorService
{
    @Autowired
    private EssayRepository essayRepository;

    @Autowired
    private S3Service s3Service;

    // @Autowired
    // private AuthRepo authRepo;

    public ResponseEntity<ApiResponse> listAll()
    {
        List<Essay> essays = essayRepository.findAll();
        
        // Sync with S3 and populate username
        essays.removeIf(essay -> {
            boolean exists = s3Service.checkIfObjectExists(essay.getS3Key());
            if (!exists) {
                essayRepository.delete(essay);
                return true;
            }
            // authRepo.findById(essay.getStudentId()).ifPresent(user -> {
            //     essay.setStudentUsername(user.getUsername());
            // });
            return false;
        });

        if (essays.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "No essays found", null));
        }
        return ResponseEntity.ok(
            new ApiResponse(true, "Essays found", essays)
        );
    }

    public ResponseEntity<ApiResponse> get(long id)
    {
        Essay essay = essayRepository.findById(id).orElse(null);
        // if (essay != null) {
        //     authRepo.findById(essay.getStudentId()).ifPresent(user -> {
        //         essay.setStudentUsername(user.getUsername());
        //     });
        // }
        return ResponseEntity.ok(
            new ApiResponse(true, "Essay found", essay)
        );
    }
}

