package com.example.historian_api.repositories.courses.quizzes.units;

import com.example.historian_api.entities.courses.quizzes.units.FinalRevisionQuestionSolution;
import com.example.historian_api.entities.keys.FinalRevisionQuestionSolutionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinalRevisionQuestionsSolutionsRepository extends JpaRepository<FinalRevisionQuestionSolution, FinalRevisionQuestionSolutionKey> {
}