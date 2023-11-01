package com.example.historian_api.dtos.responses;

import com.example.historian_api.entities.dates.GroupDate;
import lombok.Builder;

import java.util.List;

@Builder
public record
GradeGroupResponseDto(
        Long id,
        String title,
        Integer gradeId,
        List<GroupDate> dates
) {
}
