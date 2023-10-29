package com.example.historian_api.dtos.requests;

import java.math.BigDecimal;
import java.util.List;

public record AddQuizScoreRequestDto(
        BigDecimal actualScore,
        BigDecimal totalScore,
        BigDecimal time,
        List<QuestionAnswerRequestDto> questions
) {
}
