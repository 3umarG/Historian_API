package com.example.historian_api.mappers;

import com.example.historian_api.entities.dates.GroupDate;
import com.example.historian_api.entities.projections.GroupDateProjection;
import com.example.historian_api.repositories.dates.GradeGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@AllArgsConstructor
public class GroupDateProjectionToGroupDateMapper implements Function<GroupDateProjection, GroupDate> {
    @Override
    public GroupDate apply(GroupDateProjection groupDateProjection) {
        return GroupDate
                .builder()
                .id(groupDateProjection.getDateId())
                .lessonDateTime(groupDateProjection.getLessonDateTime())
                .dayName(groupDateProjection.getDayName())
                .build();
    }
}
