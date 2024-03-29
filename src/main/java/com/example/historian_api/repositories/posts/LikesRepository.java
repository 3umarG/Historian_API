package com.example.historian_api.repositories.posts;

import com.example.historian_api.entities.posts.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Like, Integer> {

    List<Like> findAllByPost_IdOrderByIdDesc(Integer postId);


    @Query("SELECT  l FROM Like l " +
           "Where l.creator.id = :creatorId " +
           "AND l.post.id= :postId " +
           "order by l.id desc ")
    Optional<Like> getLikeWithPostIdAndAndCreatorId(@Param("postId") Integer postId, @Param("creatorId") Integer userId);

    @Query(value = "select case when exists( " +
                   "    select id " +
                   "    from posts_likes " +
                   "    where creator_id = ?1 AND post_id = ?2 " +
                   ") " +
                   "then cast(1 as bit) " +
                   "else cast(0 as bit) end",nativeQuery = true)
    Boolean isStudentLikePost(Integer studentId , Integer postId);
}