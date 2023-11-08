package com.example.historian_api.repositories.courses;

import com.example.historian_api.entities.courses.QuestionImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionsImagesRepository extends JpaRepository<QuestionImage,Integer> {

    Optional<QuestionImage> findByTitle(String title);
}
