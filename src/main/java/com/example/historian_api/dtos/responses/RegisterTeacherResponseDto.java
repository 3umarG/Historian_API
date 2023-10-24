package com.example.historian_api.dtos.responses;

public record RegisterTeacherResponseDto(
        Integer id,
        String name,
        String phone,
        String role,
        String photoUrl,
        String address,
        String summery,
        String facebookUrl,
        String whatsAppUrl
) {
}
