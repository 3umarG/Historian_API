package com.example.historian_api.repositories.posts;

import com.example.historian_api.entities.posts.PostImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostsImagesRepository extends JpaRepository<PostImages, Integer> {

    Optional<PostImages> findByTitle(String title);

    @Query("select p.photoUrl " +
           "from PostImages p " +
           "where p.post.id = ?1")
    List<String> findAllPhotoUrlsByPostId(Integer postId);
}
