package com.example.historian_api.entities.projections;

import com.example.historian_api.entities.dates.GradeGroup;
import org.springframework.beans.factory.annotation.Value;

public interface GroupDateProjection {
    @Value("#{target.id}")
    Long getDateId();
    @Value("#{target.group.id}")
    Long getGradeGroupId();
    @Value("#{target.dayName}")
    String getDayName();
    @Value("#{target.lessonDateTime}")
    String getLessonDateTime();
}
