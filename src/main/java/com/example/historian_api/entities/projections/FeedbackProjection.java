package com.example.historian_api.entities.projections;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface FeedbackProjection {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.content}")
    String getContent();

    @Value("#{target.posted_on}")
    LocalDateTime getPostedOn();

    @Value("#{target.student_id}")
    Integer getStudentId();

    @Value("#{target.student_name}")
    String getStudentName();

    @Value("#{target.student_photo_url}")
    String getStudentPhotoUrl();
}
