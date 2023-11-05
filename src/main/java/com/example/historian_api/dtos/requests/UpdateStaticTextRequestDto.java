package com.example.historian_api.dtos.requests;

import jakarta.validation.constraints.NotNull;

public record UpdateStaticTextRequestDto(
        @NotNull  String newText
) {

}
