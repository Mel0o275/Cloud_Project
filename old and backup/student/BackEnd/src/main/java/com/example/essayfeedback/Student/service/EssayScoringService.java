package com.example.essayfeedback.Student.service;

// import com.example.essayfeedback.student.dto.AiModelRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.essayfeedback.Student.dto.AiModelRequest;

import java.util.Map;

@Service
public class EssayScoringService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${ai.model.url:http://localhost:8000/score}")
    private String aiModelUrl;


    public Double scoreEssay(String essayContent) {
        try {
            System.out.println("Calling AI model at: " + aiModelUrl);
            AiModelRequest request = new AiModelRequest(essayContent);

            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.postForObject(aiModelUrl, request, Map.class);

            if (response != null && response.containsKey("score")) {
                Double score = ((Number) response.get("score")).doubleValue();
                System.out.println("AI Model returned score: " + score);
                return score;
            } else {
                System.err.println("AI Model response missing score field: " + response);
            }
        } catch (Exception e) {
            System.err.println("Error calling AI model: " + e.getMessage());
            e.printStackTrace();
        }
        return 0.0;
    }
}
