package com.example.historian_api.dtos.responses;

import java.time.LocalDateTime;

public record FeedbackResponseDto(
        Integer id,
        String content,
        LocalDateTime postedOn,
        Integer studentId,
        String studentName,
        String studentPhotoUrl
) {
}
