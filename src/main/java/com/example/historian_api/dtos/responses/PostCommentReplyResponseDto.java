package com.example.historian_api.dtos.responses;

import com.example.historian_api.enums.AuthorType;

import java.time.LocalDateTime;

public record PostCommentReplyResponseDto(
        Integer id,
        String content,
        LocalDateTime createdAt,
        String createdSince,
        Integer authorId,
        String authorName,
        AuthorType authorType,
        Integer commentId
) {
}
