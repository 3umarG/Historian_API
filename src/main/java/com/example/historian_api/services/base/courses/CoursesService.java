package com.example.historian_api.services.base.courses;

import com.example.historian_api.dtos.requests.LessonCommentRequestDto;
import com.example.historian_api.dtos.responses.CourseResponseDto;
import com.example.historian_api.dtos.responses.EnrolledCourseResponseDto;
import com.example.historian_api.dtos.responses.LessonResponseDto;
import com.example.historian_api.dtos.responses.VideoCommentResponseDto;

import java.util.List;

public interface CoursesService {
    List<CourseResponseDto> getAllCoursesByGradeIdForStudent(Integer semesterId, Integer studentId);

    List<CourseResponseDto> getAllSubscribedCoursesForStudent(Integer studentId);

    List<LessonResponseDto> getUnitLessonsForStudent(Integer unitId, Integer studentId);

    EnrolledCourseResponseDto enrollCourseByStudent(Integer courseId, Integer studentId);

    List<VideoCommentResponseDto> getCommentsByLessonId(Integer lessonId);

    VideoCommentResponseDto addCommentToLesson(LessonCommentRequestDto requestDto);
}
