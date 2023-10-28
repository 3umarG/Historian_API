package com.example.historian_api.repositories.courses;

import com.example.historian_api.entities.courses.Unit;
import com.example.historian_api.entities.projections.UnitDetailsInCourseProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitsRepository extends JpaRepository<Unit, Integer> {
    @Query(value = "select units.*, count(lessons.id) as content_count " +
                   "from courses_units units " +
                   "         left join units_lessons lessons " +
                   "                   on units.id = lessons.unit_id " +
                   "where units.course_id = ?1 " +
                   "group by units.id",
            nativeQuery = true)
    List<UnitDetailsInCourseProjection> getAllUnitsDetailsByCourseId(Integer courseId);
}