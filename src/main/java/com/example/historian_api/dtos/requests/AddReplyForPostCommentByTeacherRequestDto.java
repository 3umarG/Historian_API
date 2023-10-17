package com.example.historian_api.dtos.requests;

public record AddReplyForPostCommentByTeacherRequestDto(
        Integer teacherId,
        String content,
        Integer commentId
) {
}
