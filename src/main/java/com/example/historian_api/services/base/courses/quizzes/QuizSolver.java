package com.example.historian_api.services.base.courses.quizzes;


import com.example.historian_api.dtos.requests.QuestionAnswerRequestDto;
import com.example.historian_api.entities.courses.quizzes.Question;
import com.example.historian_api.entities.courses.quizzes.QuestionSolution;
import com.example.historian_api.entities.courses.quizzes.QuizResult;
import com.example.historian_api.entities.courses.quizzes.lessons.LessonQuestion;
import com.example.historian_api.entities.users.Student;
import com.example.historian_api.services.utils.StudentsRepositoryUtils;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

public abstract class QuizSolver {

    public abstract void ensureStudentHasNotSolvedQuiz(Integer studentId, Integer quizId);

    public abstract void saveQuestionsSolutionsToDb(List<?> solutions);

    public abstract QuizResult saveQuizResult(Record dto);


    public boolean isStudentAnswerCorrect(QuestionAnswerRequestDto studentAnswerToQuestion, Question question) {
        return Objects.equals(studentAnswerToQuestion.answerIndex(), question.getCorrectAnswerIndex());
    }

    public String extractActualAnswer(LessonQuestion question) {
        return question.getAnswers().get(question.getCorrectAnswerIndex());
    }
}
