package com.example.historian_api.entities.projections;

import org.springframework.beans.factory.annotation.Value;

public interface LessonProjection {
    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.content}")
    String getContent();

    @Value("#{target.title}")
    String getTitle();

    @Value("#{target.is_free}")
    Boolean getIsFree();

    @Value("#{target.video_description}")
    String getVideoDescription();

    @Value("#{target.video_url}")
    String getVideoUrl();
    @Value("#{target.is_solved}")
    Boolean getIsSolved();



}
