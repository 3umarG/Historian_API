package com.example.historian_api.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record LoginStudentRequestDto(
        @NotBlank(message = "Phone is required")
        @NotEmpty(message = "Phone is required")
        @NotNull(message = "Phone is required")
        String phone,


        @NotBlank(message = "Device Serial is required")
        @NotEmpty(message = "Device Serial is required")
        @NotNull(message = "Device Serial is required")
        String deviceSerial

) {
}
