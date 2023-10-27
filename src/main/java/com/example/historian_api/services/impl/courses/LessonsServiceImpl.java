package com.example.historian_api.services.impl.courses;

import com.example.historian_api.dtos.responses.LessonResponseDto;
import com.example.historian_api.dtos.responses.LessonVideoResponseDto;
import com.example.historian_api.repositories.courses.LessonsRepository;
import com.example.historian_api.services.base.courses.LessonsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonsServiceImpl implements LessonsService {

    private final LessonsRepository lessonsRepository;

    @Override
    public List<LessonResponseDto> getLessonsInUnitForStudent(Integer unitId, Integer studentId) {

        return lessonsRepository.getLessonsByUnitIdForStudentId(unitId, studentId)
                .stream()
                .map(lesson -> new LessonResponseDto(
                        lesson.getId(),
                        lesson.getTitle(),
                        lesson.getContent(),
                        new LessonVideoResponseDto(lesson.getId(),
                                lesson.getTitle(),
                                lesson.getContent(),
                                lesson.getVideoUrl(),
                                lesson.getVideoDescription()),
                        lesson.getIsFree(),
                        lesson.getIsSolved()
                ))
                .toList();
    }
}
