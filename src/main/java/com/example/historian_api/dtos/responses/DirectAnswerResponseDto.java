package com.example.historian_api.dtos.responses;

import java.time.LocalDateTime;

public record DirectAnswerResponseDto(
        Integer answerId,
        String answerContent,
        LocalDateTime answeredOn,
        Integer questionId
) {
}
