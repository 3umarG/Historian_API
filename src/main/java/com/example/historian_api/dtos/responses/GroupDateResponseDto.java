package com.example.historian_api.dtos.responses;
import lombok.Builder;

import java.util.Map;

@Builder
public record GroupDateResponseDto(
        Long dateId,
        Long groupId,
         String dayName,
        String lessonDateTime
){
}
