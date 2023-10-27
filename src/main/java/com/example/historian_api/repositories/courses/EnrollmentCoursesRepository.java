package com.example.historian_api.repositories.courses;

import com.example.historian_api.entities.courses.EnrollmentCourse;
import com.example.historian_api.entities.keys.EnrollmentCourseKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentCoursesRepository extends JpaRepository<EnrollmentCourse, EnrollmentCourseKey> {
}