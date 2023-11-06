package com.example.historian_api.services.utils;

import com.example.historian_api.entities.courses.quizzes.lessons.UnitLesson;
import com.example.historian_api.entities.users.Student;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.courses.LessonsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonsRepositoryUtils {

    private final LessonsRepository lessonsRepository;

    public UnitLesson getLessonByIdOrThrowNotFound(Integer lessonId){
        return lessonsRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Lesson with that id !!"));
    }

    public boolean isNotFoundLesson(Integer lessonId) {
        return !lessonsRepository.existsById(lessonId);
    }
}
