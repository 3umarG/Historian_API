package com.example.historian_api.repositories.posts;

import com.example.historian_api.entities.posts.PostImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostsImagesRepository extends JpaRepository<PostImages, Integer> {

    Optional<PostImages> findByTitle(String title);
}
