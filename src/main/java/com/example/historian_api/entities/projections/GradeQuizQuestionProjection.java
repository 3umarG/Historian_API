package com.example.historian_api.entities.projections;

public interface GradeQuizQuestionProjection {
    Integer getId();
    Integer getCorrectAnswerIndex();
    Boolean getIsCheckedAnswer();
    String getQuestion();
    Integer getQuizId();
    String getCorrectAnswerDescription();
    String getPhotoUrl();
    String getAnswer();

    Integer getStudentId();

    String getStudentAnswer();

    Integer getStudentAnswerIndex();

    Boolean getIsStudentSucceeded();
}

