package com.example.historian_api.entities.projections;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public interface PostWithLikesAndCommentsCountsProjection {


    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.content}")
    String getContent();

    @Value("#{target.creation_date}")
    LocalDate getCreationDate();

    @Value("#{target.title}")
    String getTitle();

    @Value("#{target.teacher_id}")
    Integer getTeacherId();

    @Value("#{target.number_of_comments}")
    Integer getNumberOfComments();
    @Value("#{target.number_of_likes}")
    Integer getNumberOfLikes();
}
