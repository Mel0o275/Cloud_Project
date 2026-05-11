package com.example.essayfeedback.Student.controller;

import java.util.Map;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.essayfeedback.Student.dto.EssayRequest;
import com.example.essayfeedback.Student.entity.Essay;
import com.example.essayfeedback.Student.service.EssayService;

// import com.example.essayfeedback.student.dto.EssayRequest;
// import com.example.essayfeedback.student.entity.Essay;
// import com.example.essayfeedback.student.service.EssayService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/essays")
public class EssayController {

    private final EssayService essayService;

    public EssayController(EssayService essayService) {
        this.essayService = essayService;
    }

    @PostMapping("/submit")
    public ResponseEntity<Essay> submitEssay(@RequestBody EssayRequest request) {
        Essay savedEssay = essayService.submitEssay(request.getContent(), request.getStudentId(), request.getStudentUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEssay);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Essay>> getEssaysByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(essayService.getEssaysByStudentId(studentId));
    }

    @GetMapping
    public ResponseEntity<List<Essay>> getAllEssays() {
        return ResponseEntity.ok(essayService.getAllEssays());
    }

    @GetMapping("/{id}/presigned-url")
    public ResponseEntity<Map<String, String>> getPresignedUrl(@PathVariable Long id) {
        String url = essayService.getPresignedUrl(id);
        return ResponseEntity.ok(Map.of("url", url));
    }

    @DeleteMapping("/delete/{id}")
public void deleteUser(@PathVariable Long id) {
    essayService.deleteEssay(id);
}

}