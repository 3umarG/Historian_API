package com.example.historian_api.utils.repositories_utils.grades_quizzes;

import com.example.historian_api.entities.courses.quizzes.grades.GradeQuiz;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.courses.quizzes.grades.GradeQuizzesRepository;
import com.example.historian_api.utils.constants.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GradeQuizzesRepositoryUtils {

    private final GradeQuizzesRepository quizzesRepository;

    public GradeQuiz getQuizByIdOrThrowNotFound(Integer quizId) {
        return quizzesRepository.findById(quizId)
                .orElseThrow(() -> new NotFoundResourceException(ExceptionMessages.getNotFoundResourceMessage("Quiz")));
    }
}
