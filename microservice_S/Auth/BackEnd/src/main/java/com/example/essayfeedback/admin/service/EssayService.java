package com.example.essayfeedback.admin.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class EssayService {

    public void deleteByStudentId(Long id){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://172.31.35.129:8080/api/essays/delete/" + id);
    }


}
