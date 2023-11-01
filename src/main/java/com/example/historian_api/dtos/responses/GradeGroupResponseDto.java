package com.example.historian_api.dtos.responses;

import com.example.historian_api.entities.dates.GroupDate;
import com.example.historian_api.entities.projections.GroupDateProjection;
import lombok.Builder;
import lombok.Setter;

import java.util.List;

@Builder
public record
GradeGroupResponseDto(
        Long Id,
        String title,
        Integer gradeId,
        List<?> groupDateList
) {
}
