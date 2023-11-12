package com.example.historian_api.services.base.courses;

import com.example.historian_api.dtos.responses.CourseResponseDto;
import com.example.historian_api.dtos.responses.EnrolledSemesterResponseDto;
import com.example.historian_api.dtos.responses.SemesterResponseDto;

import java.util.List;

public interface SemestersService {
    List<SemesterResponseDto> getAllSemestersInGradeForStudent(Integer gradeId, Integer studentId);
    List<CourseResponseDto> getCoursesInSemester(Integer semesterId);
    EnrolledSemesterResponseDto subscribeStudentInSemesterByVodafoneCash(Integer studentId, Integer semesterId);
    EnrolledSemesterResponseDto subscribeStudentInSemesterByCode(Integer studentId,Integer semesterId,String code);
}
