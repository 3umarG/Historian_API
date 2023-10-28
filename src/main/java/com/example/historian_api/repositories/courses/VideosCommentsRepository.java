package com.example.historian_api.repositories.courses;

import com.example.historian_api.entities.courses.VideoComment;
import com.example.historian_api.entities.projections.LessonCommentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideosCommentsRepository extends JpaRepository<VideoComment, Integer> {

    @Query(value = "select comments.id, " +
                   "       comments.content, " +
                   "       comments.creation_date, " +
                   "       comments.lesson_id, " +
                   "       coalesce(comments.student_id, comments.teacher_id) AS author_id, " +
                   "       coalesce(s.name, t.name)                         as author_name, " +
                   "       coalesce(s.photo_url, t.photo_url)               as author_photo_url, " +
                   "       CASE " +
                   "           WHEN comments.student_id is not null THEN 'STUDENT' " +
                   "           ELSE 'TEACHER' " +
                   "           END " +
                   "       AS author_type " +
                   "from videos_comments comments " +
                   "         left join students s " +
                   "                   on s.id = comments.student_id " +
                   "         left join teachers t " +
                   "                   on t.id = comments.teacher_id " +
                   "WHERE comments.lesson_id = ?1 " +
                   "ORDER BY author_type desc ,comments.creation_date",
            nativeQuery = true)
    List<LessonCommentProjection> getCommentsByLessonId(Integer lessonId);
}
