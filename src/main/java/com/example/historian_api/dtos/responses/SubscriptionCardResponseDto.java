package com.example.historian_api.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@JsonInclude
public record SubscriptionCardResponseDto(
        Long cardId,
        String value,
        boolean isValid,
        LocalDate expiredOn,
        LocalDate creationOn,
        boolean isPaid
) {
}
