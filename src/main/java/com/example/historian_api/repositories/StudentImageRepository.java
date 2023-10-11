package com.example.historian_api.repositories;

import com.example.historian_api.entities.users.StudentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentImageRepository extends JpaRepository<StudentImage, Integer> {

    Optional<StudentImage> findByTitle(String title);
}