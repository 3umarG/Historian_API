package com.example.historian_api.repositories.courses.quizzes;

import com.example.historian_api.entities.courses.quizzes.grades.GradeQuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeQuizQuestionsRepository extends JpaRepository<GradeQuizQuestion, Integer> {

    List<GradeQuizQuestion> findAllByQuiz_IdOrderById(Integer quizId);
}