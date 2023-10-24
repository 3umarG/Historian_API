package com.example.historian_api.dtos.requests;

import org.springframework.web.multipart.MultipartFile;

public record CompetitionRequestDto(
        String title,

        String description,

        MultipartFile photo
) {
}
