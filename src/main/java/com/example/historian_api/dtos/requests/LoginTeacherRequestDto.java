package com.example.historian_api.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LoginTeacherRequestDto(
        @NotEmpty(message = "Phone is required")
        @NotNull(message = "Phone is required")
        @NotBlank(message = "Phone is required")
        String phone,

        @NotNull(message = "Password is required")
        @NotBlank(message = "Password is required")
        @NotEmpty(message = "Password is required")
        String password
) {
}
