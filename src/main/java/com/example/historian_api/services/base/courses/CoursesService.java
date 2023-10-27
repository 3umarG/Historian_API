package com.example.historian_api.services.base.courses;

import com.example.historian_api.dtos.responses.CourseResponseDto;

import java.util.List;

public interface CoursesService {
    List<CourseResponseDto> getAllCoursesByGradeIdForStudent(Integer gradeId,Integer studentId);
}
