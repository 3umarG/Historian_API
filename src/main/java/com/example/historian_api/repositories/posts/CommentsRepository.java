package com.example.historian_api.repositories.posts;

import com.example.historian_api.entities.posts.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Integer> {

    @Query(value = "select * " +
                   "from posts_comments " +
                   "where post_id = ?1 " +
                   "order by creation_date desc ",
            nativeQuery = true)
    List<Comment> findAllByPostIdOrderByCreationDateDesc(Integer postId);
}