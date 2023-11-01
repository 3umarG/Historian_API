package com.example.historian_api.services.impl.dates;

import com.example.historian_api.dtos.requests.GradeGroupRequestDto;
import com.example.historian_api.dtos.responses.GradeGroupResponseDto;
import com.example.historian_api.entities.dates.GradeGroup;
import com.example.historian_api.entities.dates.GroupDate;
import com.example.historian_api.entities.projections.GradeGroupProjection;
import com.example.historian_api.entities.projections.GroupDateProjection;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.mappers.GradeGroupProjectionToGradeGroupResponseDto;
import com.example.historian_api.mappers.GradeGroupRequestDtoToGradeGroupMapper;
import com.example.historian_api.mappers.GradeGroupToGradeGroupResponseDto;
import com.example.historian_api.mappers.GroupDateProjectionToGroupDateMapper;
import com.example.historian_api.repositories.grades.StudentGradesRepository;
import com.example.historian_api.repositories.dates.GradeGroupRepository;
import com.example.historian_api.services.base.dates.GradeGroupsServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GradeGroupServiceImpl implements GradeGroupsServices {
    
    private final GradeGroupRepository repository;
    private final StudentGradesRepository studentGradesRepository;
    private final GradeGroupRequestDtoToGradeGroupMapper gradeGroupRequestDtoToGradeGroupMapper;
    private final GradeGroupToGradeGroupResponseDto gradeGroupToGradeGroupResponseDto;
    private final GradeGroupProjectionToGradeGroupResponseDto projectionToGradeGroupResponseDto;

    @Override
    public GradeGroupResponseDto saveGradeGroup(GradeGroupRequestDto dto) throws NotFoundResourceException {
        var grade=studentGradesRepository.findById(dto.gradeId()).orElseThrow(
                ()-> new NotFoundResourceException("there is no student grade with this id")
        );
        var gradeGroup=gradeGroupRequestDtoToGradeGroupMapper.apply(dto);
        repository.save(gradeGroup);
        return gradeGroupToGradeGroupResponseDto.apply(gradeGroup);
    }

    @Override
    public List<GradeGroupResponseDto> getAllGroups() {
        return repository.findAllWithProjection().stream().map(projectionToGradeGroupResponseDto::apply).toList();
    }

    @Override
    public GradeGroupResponseDto getGroupById(Long groupId) {
        var group=repository.findByGroupIdWithProjection(groupId);
        if(group==null){
            throw new NotFoundResourceException("There is no group with that id !!");
        }
        return projectionToGradeGroupResponseDto.apply(group);
    }

    @Override
    public GradeGroupResponseDto updateGroupTitle(Long groupId, String newTitle) {
        repository.updateGroupTitleById(newTitle, groupId);
        var group=repository.findByGroupIdWithProjection(groupId);
        if(group==null){
            throw new NotFoundResourceException("There is no group with that id !!");
        }
        return projectionToGradeGroupResponseDto.apply(group);
    }

    @Override
    public List<GradeGroupResponseDto> getGroupsByGradeId(Integer gradeId) {
        studentGradesRepository.findById(gradeId).orElseThrow(
                () -> new NotFoundResourceException("There is no grade with that id !!"));
        return repository.findAllByStudentGradeId(gradeId).stream().map(projectionToGradeGroupResponseDto::apply).toList();
    }
}

