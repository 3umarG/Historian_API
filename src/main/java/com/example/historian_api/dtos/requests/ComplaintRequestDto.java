package com.example.historian_api.dtos.requests;

public record ComplaintRequestDto(
        Integer studentId,
        String content
) {
}
