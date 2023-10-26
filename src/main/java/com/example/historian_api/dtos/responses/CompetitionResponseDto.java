package com.example.historian_api.dtos.responses;

import java.time.LocalDateTime;

public record CompetitionResponseDto(
        Integer id,
        String title,
        String description,
        String photoUrl,
        Integer teacherId,
        String teacherName,
        String teacherPhotoUrl,
        LocalDateTime postedOn,
        String createdSince
) {
}
