package com.example.historian_api.repositories.competitions;

import com.example.historian_api.entities.competitions.CompetitionImage;
import com.example.historian_api.entities.users.TeacherImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompetitionsImagesRepository extends JpaRepository<CompetitionImage,Integer> {
    Optional<CompetitionImage> findByTitle(String title);
}
