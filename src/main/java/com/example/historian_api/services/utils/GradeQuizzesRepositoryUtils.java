package com.example.historian_api.services.utils;

import com.example.historian_api.entities.courses.quizzes.grades.GradeQuiz;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.courses.quizzes.grades.GradeQuizzesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GradeQuizzesRepositoryUtils {

    private final GradeQuizzesRepository quizzesRepository;

    public GradeQuiz getQuizByIdOrThrowNotFound(Integer quizId) {
        return quizzesRepository.findById(quizId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Quiz with that id !!"));
    }
}
