package com.example.historian_api.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginTeacherRequestDto(
        @NotNull
        @NotBlank
        String phone,

        @NotNull
        @NotBlank
        String password
) {
}
