package com.example.historian_api.utils.repositories_utils.lessons;

import com.example.historian_api.entities.courses.quizzes.lessons.UnitLesson;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.courses.LessonsRepository;
import com.example.historian_api.utils.constants.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonsRepositoryUtils {

    private final LessonsRepository lessonsRepository;

    public UnitLesson getLessonByIdOrThrowNotFound(Integer lessonId){
        return lessonsRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundResourceException(ExceptionMessages.getNotFoundResourceMessage("Lesson")));
    }

    public boolean isNotFoundLesson(Integer lessonId) {
        return !lessonsRepository.existsById(lessonId);
    }
}
