package com.example.historian_api.dtos.responses;

public record LessonResponseDto(
        Integer id,
        String title,
        String content,
        LessonVideoResponseDto video,
        Boolean isFree,
        Boolean isSolved
) {
}
