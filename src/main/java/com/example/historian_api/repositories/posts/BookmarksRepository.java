package com.example.historian_api.repositories.posts;

import com.example.historian_api.entities.keys.PostsBookmarksKey;
import com.example.historian_api.entities.posts.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarksRepository extends JpaRepository<Bookmark, PostsBookmarksKey> {

    List<Bookmark> findAllByStudent_IdOrderBySavedOnDesc(Integer studentId);
    Optional<Bookmark> findByPost_IdAndStudent_Id(Integer postId, Integer studentId);

    @Query(value = "select case when exists( " +
                   "    select saved_on " +
                   "    from bookmarks " +
                   "    where student_id = ?1 AND post_id = ?2 " +
                   ") " +
                   "then cast(1 as bit) " +
                   "else cast(0 as bit) end",nativeQuery = true)
    Boolean isStudentBookmarksPost(Integer studentId, Integer id);
}