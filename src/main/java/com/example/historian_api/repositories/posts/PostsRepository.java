package com.example.historian_api.repositories.posts;

import com.example.historian_api.entities.posts.Post;
import com.example.historian_api.entities.projections.PostWithLikesAndCommentsCountsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<Post, Integer> {

    @Query(value = "select p.*, " +
                   "       count(distinct comments.id) as number_of_comments, " +
                   "       count(distinct likes.id) as number_of_likes " +
                   "from posts p " +
                   "         left join posts_comments comments " +
                   "                   ON p.id = comments.post_id " +
                   "         left join posts_likes likes " +
                   "                   ON p.id = likes.post_id " +
                   "group by p.id " +
                   "order by p.id desc", nativeQuery = true)
    List<PostWithLikesAndCommentsCountsProjection> findAllPosts();


    @Query(value = "select p.*, " +
                   "       count(distinct comments.id) as number_of_comments, " +
                   "       count(distinct likes.id) as number_of_likes " +
                   "from posts p " +
                   "         left join posts_comments comments " +
                   "                   ON p.id = comments.post_id " +
                   "         left join posts_likes likes " +
                   "                   ON p.id = likes.post_id " +
                   "where p.id = ?1 " +
                   "group by p.id ",
            nativeQuery = true)
    Optional<PostWithLikesAndCommentsCountsProjection> findPostById(Integer postId);

    @Query("SELECT p " +
           "FROM Post p " +
           "LEFT JOIN p.postImages " +
           "WHERE p.id = ?1")
    Optional<Post> findByPostIdWithImages(Integer postId);
}