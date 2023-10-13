package com.example.historian_api.repositories.posts;

import com.example.historian_api.entities.posts.CommentReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepliesRepository extends JpaRepository<CommentReply, Integer> {

    List<CommentReply> findAllByComment_IdOrderByCreatedAtAsc(Integer commentId);
}