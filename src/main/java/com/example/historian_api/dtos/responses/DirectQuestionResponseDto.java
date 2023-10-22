package com.example.historian_api.dtos.responses;

import java.time.LocalDateTime;

public record DirectQuestionResponseDto(
        Integer quid,
        String questionContent,
        LocalDateTime askedOn,
        Integer studentId
) {
}
