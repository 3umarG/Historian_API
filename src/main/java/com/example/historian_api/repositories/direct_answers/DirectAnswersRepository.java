package com.example.historian_api.repositories.direct_answers;

import com.example.historian_api.entities.direct_questions.DirectAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectAnswersRepository extends JpaRepository<DirectAnswer, Integer> {
}