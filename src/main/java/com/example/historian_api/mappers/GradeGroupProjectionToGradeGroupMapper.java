package com.example.historian_api.mappers;

import com.example.historian_api.entities.dates.GradeGroup;
import com.example.historian_api.entities.dates.GroupDate;
import com.example.historian_api.entities.projections.GradeGroupProjection;
import com.example.historian_api.repositories.dates.GradeGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class GradeGroupProjectionToGradeGroupMapper implements Function<GradeGroupProjection, GradeGroup> {

    @Override
    public GradeGroup apply(GradeGroupProjection gradeGroupProjection) {
        return GradeGroup
                .builder()
                .id(gradeGroupProjection.getGroupId())
                .title(gradeGroupProjection.getTitle())
                .grade(gradeGroupProjection.getStudentGrade())
                .dates(gradeGroupProjection.getGroupDates().stream().map(groupDateProjection -> GroupDate
                        .builder()
                        .id(groupDateProjection.getDateId())
                        .lessonDateTime(groupDateProjection.getLessonDateTime())
                        .dayName(groupDateProjection.getDayName())
                        .build()).toList())
                .build();
    }
}
