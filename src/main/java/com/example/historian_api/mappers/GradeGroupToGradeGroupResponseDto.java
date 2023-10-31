package com.example.historian_api.mappers;

import com.example.historian_api.dtos.responses.GradeGroupResponseDto;
import com.example.historian_api.entities.dates.GradeGroup;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class GradeGroupToGradeGroupResponseDto implements Function<GradeGroup, GradeGroupResponseDto> {
    @Override
    public GradeGroupResponseDto apply(GradeGroup gradeGroup) {
        return GradeGroupResponseDto.builder()
                .Id(gradeGroup.getId())
                .gradeId(gradeGroup.getGrade().getId())
                .title(gradeGroup.getTitle())
                .groupDateList(gradeGroup.getDates())
                .build();
    }
}
