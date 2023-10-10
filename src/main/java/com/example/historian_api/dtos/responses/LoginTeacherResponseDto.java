package com.example.historian_api.dtos.responses;

public record LoginTeacherResponseDto(
        Integer id,
        String name,
        String phone,
        String role,
        String token,
        boolean isAuthenticated
) {
}
