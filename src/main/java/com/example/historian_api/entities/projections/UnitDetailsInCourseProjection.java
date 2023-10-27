package com.example.historian_api.entities.projections;

import org.springframework.beans.factory.annotation.Value;

public interface UnitDetailsInCourseProjection {
    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.title}")
    String getTitle();

    @Value("#{target.content}")
    String getContent();

    @Value("#{target.content_count}")
    Integer getContentCount();

}
