package com.example.historian_api.repositories.courses.quizzes.lessons;

import com.example.historian_api.entities.courses.quizzes.lessons.LessonQuizResult;
import com.example.historian_api.entities.keys.LessonQuizResultKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonQuizResultsRepository extends JpaRepository<LessonQuizResult, LessonQuizResultKey> {
    Boolean existsLessonQuizResultByStudent_IdAndLesson_Id(Integer studentId, Integer lessonId);

}