package com.example.historian_api.mappers;

import com.example.historian_api.entities.projections.GradeQuizQuestionProjection;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@JsonInclude()
public class QuizQuestionWrapper {
    private Integer questionId;
    private String question;
    private List<String> answers;
    private Integer correctAnswerIndex;
    private String correctAnswer;
    private String correctAnswerDescription;
    private String photoUrl;
    private Boolean isCheckedAnswer;
    private Integer studentId;
    private String studentAnswer;
    private Integer studentAnswerIndex;
    private Boolean isStudentSucceeded;

    @JsonIgnore
    private List<GradeQuizQuestionProjection> questionsProjection;

    public QuizQuestionWrapper(
            Integer questionId,
            String question,
            List<String> answers,
            Integer correctAnswerIndex,
            String correctAnswer,
            String correctAnswerDescription,
            String photoUrl,
            Boolean isCheckedAnswer,
            Integer studentId,
            String studentAnswer,
            Integer studentAnswerIndex,
            Boolean isStudentSucceeded) {
        this.questionId = questionId;
        this.question = question;
        this.answers = answers;
        this.studentId = studentId;
        this.isStudentSucceeded = isStudentSucceeded;
        this.correctAnswerIndex = correctAnswerIndex;
        this.studentAnswerIndex = studentAnswerIndex;
        this.correctAnswer = correctAnswer;
        this.studentAnswer = studentAnswer;
        this.correctAnswerDescription = correctAnswerDescription;
        this.photoUrl = photoUrl;
        this.isCheckedAnswer = isCheckedAnswer;
    }

    public QuizQuestionWrapper(List<GradeQuizQuestionProjection> questionsProjection) {
        this.questionsProjection = questionsProjection;
    }

    public List<QuizQuestionWrapper> reformatQuestionsWithAnswers() {
        Map<Integer, List<GradeQuizQuestionProjection>> groupedByQuestionId = questionsProjection.stream()
                .collect(Collectors.groupingBy(GradeQuizQuestionProjection::getId));

        return groupedByQuestionId.values().stream()
                .map(this::createQuizQuestionWrapper)
                .collect(Collectors.toList());
    }

    private QuizQuestionWrapper createQuizQuestionWrapper(List<GradeQuizQuestionProjection> group) {
        List<String> answers = group.stream()
                .map(GradeQuizQuestionProjection::getAnswer)
                .toList();

        return new QuizQuestionWrapper(
                group.get(0).getId(),
                group.get(0).getQuestion(),
                answers,
                group.get(0).getCorrectAnswerIndex(),
                answers.get(group.get(0).getCorrectAnswerIndex()),
                group.get(0).getCorrectAnswerDescription(),
                group.get(0).getPhotoUrl(),
                group.get(0).getIsCheckedAnswer(),
                group.get(0).getStudentId(),
                group.get(0).getStudentAnswer(),
                group.get(0).getStudentAnswerIndex(),
                group.get(0).getIsStudentSucceeded()
        );
    }

}



