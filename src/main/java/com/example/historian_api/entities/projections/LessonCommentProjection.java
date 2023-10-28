package com.example.historian_api.entities.projections;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface LessonCommentProjection {
    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.content}")
    String getContent();

    @Value("#{target.creation_date}")
    LocalDateTime getCreatedAt();

    @Value("#{target.lesson_id}")
    Integer getLessonId();

    @Value("#{target.author_id}")
    Integer getAuthorId();

    @Value("#{target.author_name}")
    String getAuthorName();

    @Value("#{target.author_photo_url}")
    String getAuthorPhotoUrl();

    @Value("#{target.author_type}")
    String getAuthorType();


}
