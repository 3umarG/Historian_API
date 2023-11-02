package com.example.historian_api.dtos.requests;

import lombok.Builder;

@Builder
public record GroupDateRequestDto (
        Long groupId,
        String dayName,
        String lessonDateTime

){
}
