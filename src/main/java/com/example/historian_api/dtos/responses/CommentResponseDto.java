package com.example.historian_api.dtos.responses;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CommentResponseDto(
        Integer commentId,
        String content,
        LocalDate creationDate,
        Integer authorId,
        String authorPhotoUrl,
        String authorName,
        Integer postId
) {
}
