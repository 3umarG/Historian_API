package com.example.historian_api.dtos.responses;

public record CompetitionResponseDto(
        Integer id,
        String title,
        String description,
        String photoUrl,
        Integer teacherId,
        String teacherName,
        String teacherPhotoUrl
) {
}
