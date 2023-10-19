package com.example.historian_api.dtos.requests;

import com.example.historian_api.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record RegisterStudentRequestDto(
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name is required")
        String name,


        @NotNull(message = "device serial is required")
        @NotBlank(message = "device serial is required")
        String deviceSerial,


        @NotNull(message = "Phone number is required")
        @NotBlank(message = "Phone number is required")
        String phone,

        @NotNull(message = "Token is required")
        @NotBlank(message = "Token is required")
        String token,

        Integer gradeId,

        MultipartFile photo
) {


}
