package com.example.historian_api.services.base.courses;

import com.example.historian_api.dtos.responses.LessonResponseDto;

import java.util.List;

public interface LessonsService {
    List<LessonResponseDto> getLessonsInUnitForStudent(Integer unitId,Integer studentId);
}
