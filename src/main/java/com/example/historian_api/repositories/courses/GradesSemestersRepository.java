package com.example.historian_api.repositories.courses;

import com.example.historian_api.entities.courses.GradeSemester;
import com.example.historian_api.entities.projections.GradeSemesterProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface GradesSemestersRepository extends JpaRepository<GradeSemester, Integer> {


    @Query(value = "select semesters.id as id, " +
                   "       semesters.name as name, " +
                   "       coalesce(subs.subscription_state, 'NOT_TAKEN') as state " +
                   "from grades_semesters semesters " +
                   "         left join subscribed_semesters subs " +
                   "                   on semesters.id = subs.semester_id and subs.student_id = ?2 " +
                   "where semesters.grade_id = ?1 " +
                   "order by semesters.id ",
            nativeQuery = true)
    List<GradeSemesterProjection> findAllSemestersByGradeIdForStudentId(Integer gradeId, Integer studentId);

    @Query(value = "select " +
                   "    semesters.id," +
                   "    semesters.name," +
                   "    coalesce(subs.subscription_state, 'NOT_TAKEN') as state " +
                   "from grades_semesters semesters " +
                   "    left join subscribed_semesters subs " +
                   "        on semesters.id = subs.semester_id and subs.student_id = ?2 " +
                   "where semesters.id = ?1",
            nativeQuery = true)
    Optional<GradeSemesterProjection> findSemesterByIdForStudent(Integer semesterId, Integer studentId);
}