package com.example.historian_api.entities.projections;

import com.example.historian_api.entities.dates.GradeGroup;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

public interface DateProjection {
    @Value("#{target.id}")
    Long getId();
    @Value("#{target.group_id}")
    Long getGroupId();
    @Value("#{target.dayName}")
    String getDayName();
    @Value("#{target.lessonDateTime}")
    String getLessonDateTime();
}

