package com.example.historian_api.dtos.requests;

import com.example.historian_api.entities.courses.quizzes.grades.GradeQuiz;
import com.example.historian_api.entities.users.Student;

import java.math.BigDecimal;

public record SavedGradeQuizResultDto(
        BigDecimal time,
        int actualQuestionsScore,
        int totalQuestionsScore,
        Student student,
        GradeQuiz quiz
) {
}
