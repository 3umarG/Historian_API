package com.example.historian_api.repositories;

import com.example.historian_api.entities.courses.StudentGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentGradesRepository extends JpaRepository<StudentGrade, Integer> {
}