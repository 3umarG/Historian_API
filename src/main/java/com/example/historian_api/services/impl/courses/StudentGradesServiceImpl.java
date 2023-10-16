package com.example.historian_api.services.impl.courses;

import com.example.historian_api.entities.courses.StudentGrade;
import com.example.historian_api.repositories.StudentGradesRepository;
import com.example.historian_api.services.base.courses.StudentGradesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentGradesServiceImpl implements StudentGradesService {

    private final StudentGradesRepository repository;

    @Override
    public StudentGrade addGrade(String gradeName) {
        var grade = new StudentGrade(gradeName);
        return repository.save(grade);
    }

    @Override
    public List<StudentGrade> getAllGrades() {
        return repository.findAll();
    }
}
