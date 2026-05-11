package com.example.essayfeedback.Student.service;

// import com.example.essayfeedback.student.entity.Essay;
// import com.example.essayfeedback.student.repository.EssayRepository;
import org.springframework.stereotype.Service;

import com.example.essayfeedback.Student.entity.Essay;
import com.example.essayfeedback.Student.repository.EssayRepository;

import java.util.List;

@Service
public class EssayService {

    private final EssayRepository essayRepository;
    private final EssayScoringService scoringService;
    private final S3Service s3Service;

    public EssayService(EssayRepository essayRepository,
                        EssayScoringService scoringService,
                        S3Service s3Service) {
        this.essayRepository = essayRepository;
        this.scoringService = scoringService;
        this.s3Service = s3Service;
    }


    public Essay submitEssay(String content, Long studentId , String studentUsername) {
        String s3Key = s3Service.uploadEssay(content);
        Double score = scoringService.scoreEssay(content);
        
        String feedback = "Good effort!"; 
        if (score < 3) feedback = "Needs improvement.";
        else if (score >= 5) feedback = "Excellent work!";

        Essay essay = new Essay(s3Key, score, feedback, studentId, studentUsername);
        return essayRepository.save(essay);
    }

    public List<Essay> getEssaysByStudentId(Long studentId) {
        return essayRepository.findByStudentId(studentId);
    }

    public List<Essay> getAllEssays() {
        return essayRepository.findAll();
    }

    public String getPresignedUrl(Long id) {
        Essay essay = essayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Essay not found"));
        return s3Service.generatePresignedUrl(essay.getS3Key());
    }

    public void deleteEssay(Long id){
        essayRepository.deleteById(id);
    }


}