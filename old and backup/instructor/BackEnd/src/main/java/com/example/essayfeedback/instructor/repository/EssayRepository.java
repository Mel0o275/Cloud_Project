// package com.example.essayfeedback.Student.repository;
package com.example.essayfeedback.instructor.repository;

// import com.example.essayfeedback.student.entity.Essay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

// import com.example.essayfeedback.Student.entity.Essay;

import java.util.List;

import com.example.essayfeedback.instructor.entity.Essay;

@Repository
public interface EssayRepository extends JpaRepository<Essay, Long> {
    List<Essay> findByStudentId(Long studentId);

    @Modifying
    @Transactional
    void deleteByStudentId(Long studentId);
}