package com.example.historian_api.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record PostRequestDto(
        @NotNull(message = "Title is required")
        @NotBlank(message = "Title is required")
        String title,

        @NotNull(message = "Content is required")
        @NotBlank(message = "Content is required")
        String content,

        @NotNull(message = "AuthorID is required")
        @NotBlank(message = "AuthorID is required")
        Integer authorId,

        MultipartFile[] photos) {

}
