package com.example.historian_api.dtos.requests;

import com.example.historian_api.entities.courses.StudentGrade;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public record GradeGroupRequestDto(
        String title,
        Integer gradeId
) {
}