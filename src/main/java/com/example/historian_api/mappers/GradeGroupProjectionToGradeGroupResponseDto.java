package com.example.historian_api.mappers;

import com.example.historian_api.dtos.responses.GradeGroupResponseDto;
import com.example.historian_api.entities.dates.GroupDate;
import com.example.historian_api.entities.projections.GradeGroupProjection;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class GradeGroupProjectionToGradeGroupResponseDto implements Function<GradeGroupProjection, GradeGroupResponseDto> {
    @Override
    public GradeGroupResponseDto apply(GradeGroupProjection gradeGroup) {

        return GradeGroupResponseDto.builder()
                .id(gradeGroup.getGroupId())
                .gradeId(gradeGroup.getStudentGrade().getId())
                .title(gradeGroup.getTitle())
                .dates(gradeGroup.getGroupDates().stream().map(groupDateProjection ->
                        GroupDate
                                .builder()
                                .id(groupDateProjection.getDateId())
                                .dayName(groupDateProjection.getDayName())
                                .lessonDateTime(groupDateProjection.getLessonDateTime())
                                .build()).toList())
                .build();
    }
}
