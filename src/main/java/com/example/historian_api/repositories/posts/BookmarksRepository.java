package com.example.historian_api.repositories.posts;

import com.example.historian_api.entities.keys.PostsBookmarksKey;
import com.example.historian_api.entities.posts.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarksRepository extends JpaRepository<Bookmark, PostsBookmarksKey> {

    List<Bookmark> findAllByPost_Id(Integer postId);
    List<Bookmark> findAllByStudent_IdOrderBySavedOnDesc(Integer studentId);
    Optional<Bookmark> findByPost_IdAndStudent_Id(Integer postId, Integer studentId);
}