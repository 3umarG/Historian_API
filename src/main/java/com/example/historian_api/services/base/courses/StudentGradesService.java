package com.example.historian_api.services.base.courses;

import com.example.historian_api.entities.courses.StudentGrade;

import java.util.List;

public interface StudentGradesService {
    StudentGrade addGrade(String gradeName);
    List<StudentGrade> getAllGrades();
}
