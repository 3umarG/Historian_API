package com.example.historian_api.repositories.posts;

import com.example.historian_api.entities.posts.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByPost_IdOrderByCreationDateDesc(Integer postId);
}