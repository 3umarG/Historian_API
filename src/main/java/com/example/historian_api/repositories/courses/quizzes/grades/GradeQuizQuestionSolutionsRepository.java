package com.example.historian_api.repositories.courses.quizzes.grades;

import com.example.historian_api.entities.courses.quizzes.grades.GradeQuizQuestionSolution;
import com.example.historian_api.entities.keys.GradeQuizQuestionSolutionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeQuizQuestionSolutionsRepository extends JpaRepository<GradeQuizQuestionSolution, GradeQuizQuestionSolutionKey> {
}