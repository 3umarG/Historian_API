package com.example.historian_api.dtos.responses;

public record SemesterResponseDto(
        Integer id,
        String name,
        String subscriptionStatus
) {
}
