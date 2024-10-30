package com.example.hotel_details_project.repository;

import com.example.hotel_details_project.model.StudentExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentExamRepository extends JpaRepository<StudentExam,Long> {
}
