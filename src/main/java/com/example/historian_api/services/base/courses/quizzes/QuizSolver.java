package com.example.historian_api.services.base.courses.quizzes;


import com.example.historian_api.dtos.requests.QuestionAnswerRequestDto;
import com.example.historian_api.entities.courses.quizzes.Question;
import com.example.historian_api.entities.courses.quizzes.QuizResult;
import com.example.historian_api.entities.courses.quizzes.lessons.LessonQuestion;

import java.util.List;
import java.util.Objects;

public interface QuizSolver {

    void ensureStudentHasNotSolvedQuiz(Integer studentId, Integer quizId);

    void saveQuestionsSolutionsToDb(List<?> solutions);

    QuizResult saveQuizResult(Record dto);


    default boolean isStudentAnswerCorrect(QuestionAnswerRequestDto studentAnswerToQuestion, Question question) {
        return Objects.equals(studentAnswerToQuestion.answerIndex(), question.getCorrectAnswerIndex());
    }

    default String extractActualAnswer(LessonQuestion question) {
        return question.getAnswers().get(question.getCorrectAnswerIndex());
    }
}
