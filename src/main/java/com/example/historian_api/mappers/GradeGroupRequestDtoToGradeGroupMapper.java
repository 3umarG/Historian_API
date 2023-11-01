package com.example.historian_api.mappers;

import com.example.historian_api.dtos.requests.GradeGroupRequestDto;
import com.example.historian_api.entities.courses.StudentGrade;
import com.example.historian_api.entities.dates.GradeGroup;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.grades.StudentGradesRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class GradeGroupRequestDtoToGradeGroupMapper implements Function<GradeGroupRequestDto, GradeGroup> {

    private final StudentGradesRepository repository;
    @Override
    public GradeGroup apply(GradeGroupRequestDto gradeGroupRequestDto) {
        return GradeGroup.builder()
                .title(gradeGroupRequestDto.title())
                .grade(repository.findById(gradeGroupRequestDto.gradeId()).get())
                .dates(new ArrayList<>())
                .build();
    }
}
