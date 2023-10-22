package com.example.historian_api.dtos.responses;

public record RegisterTeacherResponseDto(
        Integer id,
        String name,
        String phone,
        String role,
        String photoUrl
) {
}
