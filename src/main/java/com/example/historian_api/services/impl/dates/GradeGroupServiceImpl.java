package com.example.historian_api.services.impl.dates;

import com.example.historian_api.dtos.requests.GradeGroupRequestDto;
import com.example.historian_api.dtos.responses.GradeGroupResponseDto;
import com.example.historian_api.entities.dates.GradeGroup;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.mappers.GradeGroupRequestDtoToGradeGroupMapper;
import com.example.historian_api.mappers.GradeGroupToGradeGroupResponseDto;
import com.example.historian_api.repositories.grades.StudentGradesRepository;
import com.example.historian_api.repositories.dates.GradeGroupRepository;
import com.example.historian_api.services.base.dates.GradeGroupsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeGroupServiceImpl implements GradeGroupsServices {
    
    private final GradeGroupRepository repository;
    private final StudentGradesRepository studentGradesRepository;
    private final GradeGroupRequestDtoToGradeGroupMapper gradeGroupRequestDtoToGradeGroupMapper;
    private final GradeGroupToGradeGroupResponseDto gradeGroupToGradeGroupResponseDto;

    @Autowired
    public GradeGroupServiceImpl(GradeGroupRepository repository, StudentGradesRepository studentGradesRepository, GradeGroupRequestDtoToGradeGroupMapper gradeGroupRequestDtoToGradeGroupMapper, GradeGroupToGradeGroupResponseDto gradeGroupToGradeGroupResponseDto) {
        this.repository = repository;
        this.studentGradesRepository = studentGradesRepository;
        this.gradeGroupRequestDtoToGradeGroupMapper = gradeGroupRequestDtoToGradeGroupMapper;
        this.gradeGroupToGradeGroupResponseDto = gradeGroupToGradeGroupResponseDto;
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
        var group = repository.findById(groupId)
                .orElseThrow(() -> new NotFoundResourceException("There is no group with that id !!"));
        group.setTitle(newTitle);
        GradeGroup updatedGroup = repository.save(group);
        return gradeGroupToGradeGroupResponseDto.apply(updatedGroup);
    }

    @Override
    public List<GradeGroupResponseDto> getAllGroups() {
        return repository.findAll().stream().map(gradeGroupToGradeGroupResponseDto::apply).toList();
    }

    @Override
    public GradeGroupResponseDto getGroupById(Long groupId) {
        var group = repository.findById(groupId)
                .orElseThrow(() -> new NotFoundResourceException("There is no group with that id !!"));
        return gradeGroupToGradeGroupResponseDto.apply(group);
    }

    @Override
    public List<GradeGroupResponseDto> getGroupsByGradeId(Integer gradeId) {
        studentGradesRepository.findById(gradeId).orElseThrow(
                () -> new NotFoundResourceException("There is no grade with that id !!"));
        return repository.findAllByStudentGradeId(gradeId).stream().map(gradeGroupToGradeGroupResponseDto::apply).toList();
    }
}
