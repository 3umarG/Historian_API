package com.example.historian_api.entities.courses.quizzes;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionSolution {

    private Integer actualAnswerIndex;

    private Integer studentAnswerIndex;

    private String actualAnswer;

    private String studentAnswer;

    private Boolean isStudentSucceeded;
}
