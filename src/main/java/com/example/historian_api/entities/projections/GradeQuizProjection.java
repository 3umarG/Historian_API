package com.example.historian_api.entities.projections;

import org.springframework.beans.factory.annotation.Value;

public interface GradeQuizProjection {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.description}")
    String getDescription();

    @Value("#{target.title}")
    String getTitle();

    @Value("#{target.semester_id}")
    Integer getSemesterId();

    @Value("#{target.is_final}")
    Boolean getIsFinalOrNot();

    @Value("#{target.is_solved}")
    Boolean getIsSolvedOrNot();
}
