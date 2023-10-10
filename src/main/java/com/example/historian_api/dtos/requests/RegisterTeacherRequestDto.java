package com.example.historian_api.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterTeacherRequestDto(

        @NotBlank
        @NotNull
        String name,


        @NotBlank
        @NotNull
        String phone,


        @NotBlank
        @NotNull
        String password
) {
}
