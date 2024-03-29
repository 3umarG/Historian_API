package com.example.historian_api.utils.repositories_utils.lessons;

import com.example.historian_api.entities.courses.quizzes.lessons.LessonQuestion;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.courses.quizzes.lessons.LessonQuestionsRepository;
import com.example.historian_api.utils.constants.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonsQuestionsRepositoryUtils {

    private final LessonQuestionsRepository repository;


    public LessonQuestion getQuestionByIdOrThrowNotFound(Integer questionId) {
        return repository.findById(questionId)
                .orElseThrow(() -> new NotFoundResourceException(ExceptionMessages.getNotFoundResourceMessage("Question")));
    }
}
