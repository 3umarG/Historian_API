package com.example.historian_api.repositories.courses.quizzes.lessons;

import com.example.historian_api.entities.courses.quizzes.lessons.LessonQuestionSolution;
import com.example.historian_api.entities.keys.LessonQuestionSolutionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonQuestionsSolutionsRepository extends JpaRepository<LessonQuestionSolution, LessonQuestionSolutionKey> {
}