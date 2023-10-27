package com.example.historian_api.repositories.courses;

import com.example.historian_api.entities.courses.UnitLesson;
import com.example.historian_api.entities.projections.LessonProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonsRepository extends JpaRepository<UnitLesson,Integer> {

    @Query(value = "select lessons.*, " +
                   "       case " +
                   "           when results.student_id is not null then true " +
                   "           else false " +
                   "           end as is_solved " +
                   "from units_lessons lessons " +
                   "         left join lessons_quizzes_results results " +
                   "                   on lessons.id = results.lesson_id and results.student_id = ?2 " +
                   "where lessons.unit_id = ?1 " +
                   "order by lessons.id",
            nativeQuery = true)
    List<LessonProjection> getLessonsByUnitIdForStudentId(Integer unitId,Integer studentId);
}
