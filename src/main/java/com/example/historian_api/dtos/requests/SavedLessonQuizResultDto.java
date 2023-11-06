package com.example.historian_api.dtos.requests;

import com.example.historian_api.entities.courses.quizzes.lessons.UnitLesson;
import com.example.historian_api.entities.users.Student;

import java.math.BigDecimal;

public record SavedLessonQuizResultDto(
        BigDecimal time,
        int actualQuestionsScore,
        int totalQuestionsScore,
        Student student,
        UnitLesson lesson
) {
}
