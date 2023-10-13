package com.example.historian_api.dtos.responses;

import java.time.LocalDate;
import java.util.List;

public record BookmarkResponseDto(
        Integer postId,
        String postTitle,
        String postContent,
        Integer studentId,
        List<String> images,
        LocalDate savedOn
) {
}
