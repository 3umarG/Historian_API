package com.example.historian_api.services.impl.dates;

import com.example.historian_api.dtos.requests.GradeGroupRequestDto;
import com.example.historian_api.dtos.responses.GradeGroupResponseDto;
import com.example.historian_api.entities.dates.GradeGroup;
import com.example.historian_api.entities.projections.GradeGroupProjection;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.mappers.GradeGroupProjectionToGradeGroupResponseDto;
import com.example.historian_api.mappers.GradeGroupRequestDtoToGradeGroupMapper;
import com.example.historian_api.mappers.GradeGroupToGradeGroupResponseDto;
import com.example.historian_api.repositories.grades.StudentGradesRepository;
import com.example.historian_api.repositories.dates.GradeGroupRepository;
import com.example.historian_api.services.base.dates.GradeGroupsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeGroupServiceImpl implements GradeGroupsServices {
    
    private final GradeGroupRepository repository;
    private final StudentGradesRepository studentGradesRepository;
    private final GradeGroupRequestDtoToGradeGroupMapper gradeGroupRequestDtoToGradeGroupMapper;
    private final GradeGroupToGradeGroupResponseDto gradeGroupToGradeGroupResponseDto;
    private final GradeGroupProjectionToGradeGroupResponseDto projectionToGradeGroupResponseDto;

    @Autowired
    public GradeGroupServiceImpl(GradeGroupRepository repository, StudentGradesRepository studentGradesRepository, GradeGroupRequestDtoToGradeGroupMapper gradeGroupRequestDtoToGradeGroupMapper, GradeGroupToGradeGroupResponseDto gradeGroupToGradeGroupResponseDto, GradeGroupProjectionToGradeGroupResponseDto projectionToGradeGroupResponseDto) {
        this.repository = repository;
        this.studentGradesRepository = studentGradesRepository;
        this.gradeGroupRequestDtoToGradeGroupMapper = gradeGroupRequestDtoToGradeGroupMapper;
        this.gradeGroupToGradeGroupResponseDto = gradeGroupToGradeGroupResponseDto;
        this.projectionToGradeGroupResponseDto = projectionToGradeGroupResponseDto;
    }

    @Override
    public GradeGroupResponseDto saveGradeGroup(GradeGroupRequestDto dto) throws NotFoundResourceException {
        var studentGrade = studentGradesRepository.findById(dto.gradeId())
                .orElseThrow(() -> new NotFoundResourceException("There is no grade with that id !!"));
        GradeGroup gradeGroup=gradeGroupRequestDtoToGradeGroupMapper.apply(dto);
        gradeGroup.setGrade(studentGrade);
        repository.save(gradeGroup);
        return gradeGroupToGradeGroupResponseDto.apply(gradeGroup);
    }

    @Override
    public GradeGroupResponseDto updateGroupTitle(Long groupId, String newTitle) throws NotFoundResourceException {
        GradeGroupProjection group = repository.findAllWithProjection().stream().filter(gradeGroupProjection -> gradeGroupProjection.getGroupId()==groupId).findFirst().orElseThrow(
                ()->new NotFoundResourceException("There is no group with that id !!")
        );
        GradeGroupResponseDto dto=projectionToGradeGroupResponseDto.apply(group);
        GradeGroup gradeGroup=GradeGroup
                .builder()
                .id(dto.Id())
                .title(dto.title())
                .grade(studentGradesRepository.findById(dto.gradeId()).get())
                .dates(dto.groupDateList())
                .build();
        gradeGroup.setTitle(newTitle);
        GradeGroup updatedGroup = repository.save(gradeGroup);
        return gradeGroupToGradeGroupResponseDto.apply(updatedGroup);
    }

    @Override
    public List<GradeGroupResponseDto> getAllGroups() {
        return repository.findAllWithProjection().stream().map(projectionToGradeGroupResponseDto::apply).toList();
    }

    @Override
    public GradeGroupResponseDto getGroupById(Long groupId) {
        GradeGroupProjection group = repository.findAllWithProjection().stream().filter(gradeGroupProjection -> gradeGroupProjection.getGroupId()==groupId).findFirst().orElseThrow(
                ()->new NotFoundResourceException("There is no group with that id !!")
        );
        return projectionToGradeGroupResponseDto.apply(group);
    }

    @Override
    public List<GradeGroupResponseDto> getGroupsByGradeId(Integer gradeId) {
        studentGradesRepository.findById(gradeId).orElseThrow(
                () -> new NotFoundResourceException("There is no grade with that id !!"));
        return repository.findAllByStudentGradeId(gradeId).stream().map(projectionToGradeGroupResponseDto::apply).toList();
    }
}
