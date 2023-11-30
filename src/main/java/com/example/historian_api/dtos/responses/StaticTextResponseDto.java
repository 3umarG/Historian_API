package com.example.historian_api.dtos.responses;

import lombok.Builder;

import java.util.List;

@Builder
public record StaticTextResponseDto(
        String aboutText,
        String privacyText,
        String googlePlayLink,
        String appStoreLink,
        String vodafoneCash,
        List<String> technicalSupportContactsInfo
) {
}
