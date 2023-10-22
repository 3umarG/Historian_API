package com.example.historian_api.dtos.responses;

public record DirectQuestionWithAnswerResponseDto(
        DirectQuestionResponseDto question,
        Boolean isSolved,
        DirectAnswerResponseDto answer
) {
}
