package com.example.historian_api.services.utils;

import com.example.historian_api.entities.courses.quizzes.grades.GradeQuizQuestion;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.courses.quizzes.grades.GradeQuizQuestionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GradeQuizQuestionsRepositoryUtils {

    private final GradeQuizQuestionsRepository gradeQuizQuestionsRepository;

    public GradeQuizQuestion getQuestionByIdOrThrowNotFound(Integer questionId) {
        return gradeQuizQuestionsRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Question with that id !!"));

    }
}
