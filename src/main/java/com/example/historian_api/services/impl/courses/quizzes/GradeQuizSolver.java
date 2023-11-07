package com.example.historian_api.services.impl.courses.quizzes;

import com.example.historian_api.dtos.requests.SavedGradeQuizResultDto;
import com.example.historian_api.entities.courses.quizzes.QuizResult;
import com.example.historian_api.entities.courses.quizzes.grades.GradeQuizQuestionSolution;
import com.example.historian_api.entities.courses.quizzes.grades.GradeQuizResult;
import com.example.historian_api.entities.keys.GradeQuizResultKey;
import com.example.historian_api.exceptions.AlreadyEnrolledCourseException;
import com.example.historian_api.repositories.courses.quizzes.grades.GradeQuizQuestionSolutionsRepository;
import com.example.historian_api.repositories.courses.quizzes.grades.GradeQuizzesResultsRepository;
import com.example.historian_api.services.base.courses.quizzes.QuizSolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("GradeQuizSolver")
public class GradeQuizSolver extends QuizSolver {

    private final GradeQuizzesResultsRepository quizzesResultsRepository;
    private final GradeQuizQuestionSolutionsRepository questionSolutionsRepository;

    @Override
    public void ensureStudentHasNotSolvedQuiz(Integer studentId, Integer quizId) {
        if (quizzesResultsRepository.existsGradeQuizResultByStudent_IdAndQuiz_Id(studentId, quizId)) {
            throw new AlreadyEnrolledCourseException("Student already solved this quiz !!");
        }
    }

    @Override
    public void saveQuestionsSolutionsToDb(List<?> solutions) {
        questionSolutionsRepository.saveAll((List<GradeQuizQuestionSolution>)solutions);
    }

    @Override
    public QuizResult saveQuizResult(Record dto) {
        var savedQuizResultDto = (SavedGradeQuizResultDto) dto;

        var quizResultKey = new GradeQuizResultKey(
                savedQuizResultDto.student().getId(),
                savedQuizResultDto.quiz().getId()
        );

        var solutionPercentage = ((float) savedQuizResultDto.actualQuestionsScore() / (float) savedQuizResultDto.totalQuestionsScore()) * 100;

        var quizResult = new GradeQuizResult(quizResultKey,
                savedQuizResultDto.quiz(),
                savedQuizResultDto.student(),
                BigDecimal.valueOf(solutionPercentage),
                savedQuizResultDto.time(),
                BigDecimal.valueOf(savedQuizResultDto.totalQuestionsScore()),
                BigDecimal.valueOf(savedQuizResultDto.actualQuestionsScore())
        );

        return quizzesResultsRepository.save(quizResult);
    }
}
