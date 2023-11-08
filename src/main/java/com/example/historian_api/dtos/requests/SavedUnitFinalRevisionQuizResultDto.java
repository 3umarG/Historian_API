package com.example.historian_api.dtos.requests;

import com.example.historian_api.entities.courses.quizzes.units.Unit;
import com.example.historian_api.entities.users.Student;

import java.math.BigDecimal;

public record SavedUnitFinalRevisionQuizResultDto(

        BigDecimal time,
        int actualQuestionsScore,
        int totalQuestionsScore,
        Student student,
        Unit unit

) {
}
