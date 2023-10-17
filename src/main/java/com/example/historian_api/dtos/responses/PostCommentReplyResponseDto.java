package com.example.historian_api.dtos.responses;

import com.example.historian_api.enums.AuthorType;

import java.time.LocalDateTime;

public record PostCommentReplyResponseDto(
        Integer id,
        String content,
        LocalDateTime createdAt,
        Integer authorId,
        AuthorType authorType,
        Integer commentId
) {
}
