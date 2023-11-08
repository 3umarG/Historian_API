package com.example.historian_api.services.impl.quizzes_solvers;

import com.example.historian_api.dtos.requests.SavedLessonQuizResultDto;
import com.example.historian_api.entities.courses.quizzes.QuizResult;
import com.example.historian_api.entities.courses.quizzes.lessons.LessonQuestionSolution;
import com.example.historian_api.entities.courses.quizzes.lessons.LessonQuizResult;
import com.example.historian_api.entities.keys.LessonQuizResultKey;
import com.example.historian_api.exceptions.AlreadyEnrolledCourseException;
import com.example.historian_api.repositories.courses.quizzes.lessons.LessonQuestionsSolutionsRepository;
import com.example.historian_api.repositories.courses.quizzes.lessons.LessonQuizResultsRepository;
import com.example.historian_api.services.base.courses.quizzes.QuizSolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("LessonQuizSolver")
public class LessonQuizSolver implements QuizSolver {

    private final LessonQuizResultsRepository lessonQuizResultsRepository;
    private final LessonQuestionsSolutionsRepository questionsSolutionsRepository;


    @Override
    public void ensureStudentHasNotSolvedQuiz(Integer studentId, Integer quizId) {
        if (lessonQuizResultsRepository.existsLessonQuizResultByStudent_IdAndLesson_Id(studentId, quizId)) {
            throw new AlreadyEnrolledCourseException("Student already solved this lesson quiz !!");
        }
    }

    @Override
    public void saveQuestionsSolutionsToDb(List<?> solutions) {
        questionsSolutionsRepository.saveAll((List<LessonQuestionSolution>) solutions);
    }

    @Override
    public QuizResult saveQuizResult(Record dto) {
        var savedLessonQuizResultDto = (SavedLessonQuizResultDto) dto;

        var quizResultKey = new LessonQuizResultKey(
                savedLessonQuizResultDto.lesson().getId(),
                savedLessonQuizResultDto.student().getId());

        var solutionPercentage = ((float) savedLessonQuizResultDto.actualQuestionsScore() / (float) savedLessonQuizResultDto.totalQuestionsScore()) * 100;

        var quizResult = new LessonQuizResult(quizResultKey,
                savedLessonQuizResultDto.student(),
                savedLessonQuizResultDto.lesson(),
                BigDecimal.valueOf(solutionPercentage),
                savedLessonQuizResultDto.time(),
                BigDecimal.valueOf(savedLessonQuizResultDto.totalQuestionsScore()),
                BigDecimal.valueOf(savedLessonQuizResultDto.actualQuestionsScore())
        );
        return lessonQuizResultsRepository.save(quizResult);
    }
}
