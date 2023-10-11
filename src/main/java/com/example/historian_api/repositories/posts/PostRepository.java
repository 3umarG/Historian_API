package com.example.historian_api.repositories.posts;

import com.example.historian_api.entities.posts.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT distinct p " +
           "FROM Post p " +
           "LEFT JOIN  p.comments c " +
           "LEFT JOIN p.likes l " +
           "ORDER BY p.id desc ")

    // TODO : this for only number of comments and likes without details
//    @Query(value = "select " +
//                   "    p.*," +
//                   "    count(c.post_id) as number_of_comments," +
//                   "    count(l.post_id) as number_of_likes " +
//                   "from posts p " +
//                   "    left join posts_comments c " +
//                   "        ON p.id = c.post_id " +
//                   "    left join posts_likes l " +
//                   "        ON p.id = l.post_id " +
//                   "group by p.id " +
//                   "order by p.id desc ", nativeQuery = true)
    List<Post> findAllPosts();


    @Query("SELECT DISTINCT p " +
           "FROM Post p " +
           "LEFT JOIN  p.likes l " +
           "LEFT JOIN  p.comments c " +
           "WHERE p.id = ?1")
    Optional<Post> findEagerById(Integer postId);
}