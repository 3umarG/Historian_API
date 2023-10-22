package com.example.historian_api.dtos.responses;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record CommentResponseDto(
        Integer commentId,
        String content,
        LocalDateTime creationDate,
        String createdSince,
        Integer authorId,
        String authorPhotoUrl,
        String authorName,
        Integer postId
) {
}
