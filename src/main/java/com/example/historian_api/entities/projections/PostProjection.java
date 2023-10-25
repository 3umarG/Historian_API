package com.example.historian_api.entities.projections;

import org.springframework.beans.factory.annotation.Value;

public interface PostProjection {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.title}")
    String getTitle();

    @Value("#{target.content}")
    String getContent();

    @Value("#{target.teacher_id}")
    Integer getTeacherId();
}
