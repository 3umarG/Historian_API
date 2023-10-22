package com.example.historian_api.entities.projections;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface QuestionWithAnswerProjection {

    @Value("#{target.question_Id}")
    Integer getQuestionId();

    @Value("#{target.answer_Id}")
    Integer getAnswerId();

    @Value("#{target.q_content}")
    String getQuestion();

    @Value("#{target.ans_content}")
    String getAnswer();

    @Value("#{target.asked_on}")
    LocalDateTime getAskedOnTime();

    @Value("#{target.replied_on}")
    LocalDateTime getAnsweredOnTime();

    @Value("#{target.student_id}")
    Integer getStudentId();

    @Value("#{target.student_name}")
    String getStudentName();

    @Value("#{target.teacher_id}")
    Integer getTeacherId();

    @Value("#{target.teacher_name}")
    String getTeacherName();

    @Value("#{target.teacher_photo_url}")
    String getTeacherPhotoUrl();
}
