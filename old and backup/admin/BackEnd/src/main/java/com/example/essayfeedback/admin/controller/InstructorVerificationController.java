package com.example.essayfeedback.admin.controller;

import com.example.essayfeedback.admin.dto.InstructorPendingDto;
import com.example.essayfeedback.admin.dto.InstructorVerificationDecisionDto;
import com.example.essayfeedback.admin.service.InstructorVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin/instructors")
public class InstructorVerificationController {

    private final InstructorVerificationService verificationService;

    public InstructorVerificationController(InstructorVerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getPendingInstructors() {
        List<InstructorPendingDto> pendingInstructors = verificationService.getPendingInstructors();
        return ResponseEntity.ok(pendingInstructors);
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<?> verifyInstructor(@PathVariable Long id, @RequestBody InstructorVerificationDecisionDto decision) {
        String resultMessage = verificationService.verifyInstructor(id, decision);
        return ResponseEntity.ok(resultMessage);
    }
}
