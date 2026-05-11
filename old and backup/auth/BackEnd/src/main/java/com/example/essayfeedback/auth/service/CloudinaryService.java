package com.example.essayfeedback.auth.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.essayfeedback.auth.entity.Instructor;
import com.example.essayfeedback.auth.repo.instructorRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import io.jsonwebtoken.io.IOException;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private instructorRepository instructorRepository;

    public String uploadFile(MultipartFile file) throws java.io.IOException {
        try {
            Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.emptyMap()
            );
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Upload failed");
        }
    }


public void uploadCertificates(
        Long id,
        List<MultipartFile> files) {

    Instructor instructor = new Instructor();
    instructor.setId(id);

    List<String> urls = files.stream()
        .map(new Function<MultipartFile, String>() {
        @Override
        public String apply(MultipartFile file) {
            try {
                return uploadFile(file);
            } catch (java.io.IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    })
        .toList();

    instructor.setCertificates(urls);
    instructorRepository.save(instructor);

    // return ResponseEntity.ok(
    //     new ApiResponse(
    //         true,
    //         "Certificates uploaded successfully",
    //         null,
    //         null
    //     )
    // );
}

public Instructor getinstructor(Long id) {
    return instructorRepository.findById(id).orElseThrow();
}


}