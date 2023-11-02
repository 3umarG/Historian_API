package com.example.historian_api.dtos.requests;

public record LessonCommentRequestDto(
        Integer studentId,
        Integer lessonId,
        String content
) {
}
