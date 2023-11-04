package com.example.historian_api.dtos.requests;


import jakarta.validation.constraints.NotNull;

public record UpdatedComplaintContentRequestDto(
      @NotNull Long complaintId,
      @NotNull  String newContent
) {
}
