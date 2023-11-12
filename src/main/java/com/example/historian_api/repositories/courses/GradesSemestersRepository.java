package com.example.historian_api.repositories.courses;

import com.example.historian_api.entities.courses.GradeSemester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  GradesSemestersRepository extends JpaRepository<GradeSemester, Integer> {
}