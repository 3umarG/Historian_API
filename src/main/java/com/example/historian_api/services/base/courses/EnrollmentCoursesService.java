package com.example.historian_api.services.base.courses;

import com.example.historian_api.dtos.responses.EnrolledCourseResponseDto;

public interface EnrollmentCoursesService {
    EnrolledCourseResponseDto enrollCourseByStudent(Integer courseId, Integer studentId);
}
