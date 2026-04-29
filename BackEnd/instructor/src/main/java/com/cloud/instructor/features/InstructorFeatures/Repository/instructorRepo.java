package com.cloud.instructor.features.InstructorFeatures.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloud.instructor.features.InstructorFeatures.entity.student;

@Repository 
public interface instructorRepo extends JpaRepository<student, Long> { 
    
}