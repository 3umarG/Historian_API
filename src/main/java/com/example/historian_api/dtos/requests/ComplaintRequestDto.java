package com.example.historian_api.dtos.requests;

import jakarta.validation.constraints.NotNull;

public record ComplaintRequestDto(
      @NotNull Integer studentId,
      @NotNull  String content
) {
}
