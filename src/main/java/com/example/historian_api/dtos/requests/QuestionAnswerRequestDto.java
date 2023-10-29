package com.example.historian_api.dtos.requests;

public record QuestionAnswerRequestDto(
        Integer questionId,
        Integer answerIndex,
        String answer
) {
}
