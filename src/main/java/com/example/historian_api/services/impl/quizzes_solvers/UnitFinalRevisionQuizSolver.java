package com.example.historian_api.services.impl.quizzes_solvers;

import com.example.historian_api.dtos.requests.SavedUnitFinalRevisionQuizResultDto;
import com.example.historian_api.entities.courses.quizzes.QuizResult;
import com.example.historian_api.entities.courses.quizzes.units.FinalRevisionQuestionSolution;
import com.example.historian_api.entities.courses.quizzes.units.FinalRevisionResult;
import com.example.historian_api.entities.keys.FinalRevisionResultKey;
import com.example.historian_api.exceptions.AlreadyEnrolledCourseException;
import com.example.historian_api.repositories.courses.quizzes.units.FinalRevisionQuestionsSolutionsRepository;
import com.example.historian_api.repositories.courses.quizzes.units.FinalRevisionResultsRepository;
import com.example.historian_api.services.base.courses.quizzes.QuizSolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("UnitQuizSolver")
public class UnitFinalRevisionQuizSolver implements QuizSolver {

    private final FinalRevisionResultsRepository finalRevisionResultsRepository;
    private final FinalRevisionQuestionsSolutionsRepository questionsSolutionsRepository;

    @Override
    public void ensureStudentHasNotSolvedQuiz(Integer studentId, Integer quizId) {
        if (finalRevisionResultsRepository.existsFinalRevisionResultByStudent_IdAndUnit_Id(studentId, quizId)){
            throw new AlreadyEnrolledCourseException("Student already solved this quiz !!");
        }
    }

    @Override
    public void saveQuestionsSolutionsToDb(List<?> solutions) {
        questionsSolutionsRepository.saveAll((List<FinalRevisionQuestionSolution>) solutions);
    }

    @Override
    public QuizResult saveQuizResult(Record dto) {

        var savedQuizResultDto = (SavedUnitFinalRevisionQuizResultDto) dto;

        var quizResultKey = new FinalRevisionResultKey(
                savedQuizResultDto.student().getId(),
                savedQuizResultDto.unit().getId()
        );

        var solutionPercentage = ((float) savedQuizResultDto.actualQuestionsScore() / (float) savedQuizResultDto.totalQuestionsScore()) * 100;

        var quizResult = new FinalRevisionResult(
                savedQuizResultDto.student(),
                BigDecimal.valueOf(solutionPercentage),
                savedQuizResultDto.time(),
                BigDecimal.valueOf(savedQuizResultDto.totalQuestionsScore()),
                BigDecimal.valueOf(savedQuizResultDto.actualQuestionsScore()),
                quizResultKey,
                savedQuizResultDto.unit()
        );

        return finalRevisionResultsRepository.save(quizResult);
    }
}
