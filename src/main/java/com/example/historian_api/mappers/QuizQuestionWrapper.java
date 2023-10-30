package com.example.historian_api.mappers;

import com.example.historian_api.entities.projections.GradeQuizQuestionProjection;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class QuizQuestionWrapper {
    private Integer questionId;
    private String question;
    private List<String> answers;
    private Integer correctAnswerIndex;
    private Integer studentAnswerIndex;
    private String correctAnswer;
    private String studentAnswer;
    private String correctAnswerDescription;
    private String photoUrl;
    private Boolean isCheckedAnswer;

    @JsonIgnore
    private List<GradeQuizQuestionProjection> questionsProjection;

    public QuizQuestionWrapper(
            Integer questionId,
            String question,
            List<String> answers,
            Integer correctAnswerIndex,
            Integer studentAnswerIndex,
            String correctAnswer,
            String studentAnswer,
            String correctAnswerDescription,
            String photoUrl,
            Boolean isCheckedAnswer) {
        this.questionId = questionId;
        this.question = question;
        this.answers = answers;
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
                null, // TODO : change in the next endpoint
                answers.get(group.get(0).getCorrectAnswerIndex()),
                null,
                group.get(0).getCorrectAnswerDescription(),
                group.get(0).getPhotoUrl(),
                group.get(0).getIsCheckedAnswer()
        );
    }

}



