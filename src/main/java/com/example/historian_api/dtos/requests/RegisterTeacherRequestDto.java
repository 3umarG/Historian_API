package com.example.historian_api.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record RegisterTeacherRequestDto(

        @NotBlank(message = "Name is required")
        @NotNull(message = "Name is required")
        @NotEmpty(message = "Name is required")
        String name,


        @NotBlank(message = "Phone is required")
        @NotNull(message = "Phone is required")
        @NotEmpty(message = "Phone is required")
        String phone,


        @NotBlank(message = "Password is required")
        @NotNull(message = "Password is required")
        @NotEmpty(message = "Password is required")
        String password,

        MultipartFile photo
) {
}
