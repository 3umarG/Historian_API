package com.example.historian_api.repositories.posts;

import com.example.historian_api.entities.posts.CommentReply;
import com.example.historian_api.entities.projections.CommentReplyProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepliesRepository extends JpaRepository<CommentReply, Integer> {

    @Query(value = "select replies.id, " +
                   "       replies.content, " +
                   "       replies.created_at, " +
                   "       replies.comment_id, " +
                   "       coalesce(replies.student_id, replies.teacher_id) AS author_id, " +
                   "       coalesce(s.name, t.name)                         as author_name, " +
                   "       coalesce(s.photo_url, t.photo_url)               as author_photo_url, " +
                   "       CASE " +
                   "           WHEN replies.student_id is not null THEN 'STUDENT' " +
                   "           ELSE 'TEACHER' " +
                   "           END " +
                   "       AS author_type " +
                   "from posts_comments_replies replies " +
                   "         left join students s " +
                   "                   on s.id = replies.student_id " +
                   "         left join teachers t " +
                   "                   on t.id = replies.teacher_id " +
                   "WHERE replies.comment_id = ?1 " +
                   "ORDER BY replies.created_at",
            nativeQuery = true)
    List<CommentReplyProjection> findAllByCommentIdOrderByCreatedAt(Integer commentId);
}