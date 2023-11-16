package com.example.historian_api.dtos.responses;

public record GradeQuizResponseDto(
        Integer id,
        String title,
        String description,
        Boolean isSolved,
        Boolean isFinal,
        Integer semesterId
) {
}
