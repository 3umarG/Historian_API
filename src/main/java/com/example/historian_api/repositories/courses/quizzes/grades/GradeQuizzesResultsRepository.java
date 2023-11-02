package com.example.historian_api.repositories.courses.quizzes.grades;

import com.example.historian_api.entities.courses.quizzes.grades.GradeQuizResult;
import com.example.historian_api.entities.keys.GradeQuizResultKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeQuizzesResultsRepository extends JpaRepository<GradeQuizResult, GradeQuizResultKey> {
    Boolean existsGradeQuizResultByStudent_IdAndQuiz_Id(Integer studentId, Integer quizId);
}