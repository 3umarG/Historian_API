package com.example.historian_api.dtos.requests;

public record AddReplyForPostCommentByStudentRequestDto(

        Integer studentId,
        String content,
        Integer commentId

) {
}
