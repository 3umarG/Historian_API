package com.example.historian_api.repositories.users;

import com.example.historian_api.entities.users.TeacherImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherImagesRepository extends JpaRepository<TeacherImage,Integer> {
    Optional<TeacherImage> findByTitle(String title);
}
