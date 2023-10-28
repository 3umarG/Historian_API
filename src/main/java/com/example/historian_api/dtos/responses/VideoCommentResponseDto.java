package com.example.historian_api.dtos.responses;

import java.time.LocalDateTime;

public record VideoCommentResponseDto(
        Integer id,
        String content,
        LocalDateTime createdAt,
        String createdSince,
        Integer authorId,
        String authorName,
        String authorType,
        String authorPhotoUrl,
        Integer lessonId
) {
}
