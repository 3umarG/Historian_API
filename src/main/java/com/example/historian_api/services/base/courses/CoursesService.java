package com.example.historian_api.services.base.courses;

import com.example.historian_api.dtos.responses.CourseResponseDto;
import com.example.historian_api.dtos.responses.EnrolledCourseResponseDto;
import com.example.historian_api.dtos.responses.LessonResponseDto;

import java.util.List;

public interface CoursesService {
    List<CourseResponseDto> getAllCoursesByGradeIdForStudent(Integer gradeId, Integer studentId);

    List<CourseResponseDto> getAllSubscribedCoursesForStudent(Integer studentId);

    List<LessonResponseDto> getUnitLessonsForStudent(Integer unitId, Integer studentId);

    EnrolledCourseResponseDto enrollCourseByStudent(Integer courseId, Integer studentId);
}
