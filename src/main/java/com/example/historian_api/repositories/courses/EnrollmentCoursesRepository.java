package com.example.historian_api.repositories.courses;

import com.example.historian_api.entities.courses.EnrollmentCourse;
import com.example.historian_api.entities.keys.EnrollmentCourseKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentCoursesRepository extends JpaRepository<EnrollmentCourse, EnrollmentCourseKey> {

    @Query(value = "select case when exists( " +
                   "    select student_id " +
                   "    from enrollment_courses " +
                   "    where student_id = ?1 AND course_id = ?2 " +
                   ") " +
                   "then cast(1 as bit) " +
                   "else cast(0 as bit) end",
            nativeQuery = true)
    Boolean isStudentEnrolledCourses(Integer studentId, Integer courseId);
}