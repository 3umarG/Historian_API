package com.example.historian_api.dtos.responses;

import java.util.List;

public record CourseResponseDto(
        Integer id,
        String title,
        String state,
        List<UnitResponseDto> units
) {
}
