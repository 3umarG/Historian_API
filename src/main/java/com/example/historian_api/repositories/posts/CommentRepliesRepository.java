package com.example.historian_api.repositories.posts;

import com.example.historian_api.entities.posts.CommentReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepliesRepository extends JpaRepository<CommentReply, Integer> {

    @Query(value = "select * " +
                   "from posts_comments_replies " +
                   "where comment_id = ?1 " +
                   "order by created_at ",
            nativeQuery = true)
    List<CommentReply> findAllByCommentIdOrderByCreatedAt(Integer commentId);
}