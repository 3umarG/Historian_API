package com.example.historian_api.repositories.courses;

import com.example.historian_api.entities.courses.Course;
import com.example.historian_api.entities.projections.GradeCoursesInformationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursesRepository extends JpaRepository<Course, Integer> {

    @Query(value = "select c.id, " +
                   "       c.title, " +
                   "       coalesce(ec.state, 'NOT_TAKEN') as state " +
                   "from courses c " +
                   "         left join enrollment_courses ec " +
                   "                   on c.id = ec.course_id and ec.student_id = ?2 " +
                   "where c.semester_id = ?1 " +
                   "order by c.id ",
            nativeQuery = true)
    List<GradeCoursesInformationProjection> getAllCoursesBySemesterIdWithEnrollmentStateForStudent(Integer semesterId, Integer studentId);


    @Query(value = "select c.id, " +
                   "       c.title, " +
                   "       coalesce(ec.state, 'NOT_TAKEN') as state " +
                   "from courses c " +
                   "          join enrollment_courses ec " +
                   "                   on c.id = ec.course_id and ec.student_id = ?1 " +
                   "where ec.state = ?2 " +
                   "order by c.id ",
            nativeQuery = true)
    List<GradeCoursesInformationProjection> getAllCoursesWithStateByStudentId(Integer studentId, String status);

    @Query(value = "select c.id,c.title " +
                   "from courses c " +
                   "where c.semester_id = ?1 " +
                   "order by c.id",
    nativeQuery = true)
    List<GradeCoursesInformationProjection> findCoursesBySemesterId(Integer semesterId);
}