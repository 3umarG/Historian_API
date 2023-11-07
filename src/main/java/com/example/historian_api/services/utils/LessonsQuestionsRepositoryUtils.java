package com.example.historian_api.services.utils;

import com.example.historian_api.entities.courses.quizzes.lessons.LessonQuestion;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.courses.quizzes.lessons.LessonQuestionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonsQuestionsRepositoryUtils {

    private final LessonQuestionsRepository repository;


    public LessonQuestion getQuestionByIdOrThrowNotFound(Integer questionId) {
        return repository.findById(questionId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Question with that id !!"));
    }
}
