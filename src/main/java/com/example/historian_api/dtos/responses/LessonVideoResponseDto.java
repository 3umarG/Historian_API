package com.example.historian_api.dtos.responses;

public record LessonVideoResponseDto(
        Integer lessonId,
        String lessonTitle,
        String lessonContent,
        String videoUrl,
        String videoDescription

) {
}
