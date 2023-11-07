package com.example.historian_api.dtos.responses;

import com.example.historian_api.mappers.QuizWithQuestionsWrapper;

import java.math.BigDecimal;
import java.util.List;

public record QuizResultWithQuestionsResponseDto(
         BigDecimal solutionPercentage,

         BigDecimal takenTimeToSolveInSeconds,

         BigDecimal totalScore,

         BigDecimal actualScore,
         List<QuizWithQuestionsWrapper> questions
) {
}
