package com.example.historian_api.dtos.responses;
import lombok.Builder;

@Builder
public record GroupDateResponseDto(
        Long id,
        Long groupId,
         String dayName,
        String lessonDateTime
){
}
