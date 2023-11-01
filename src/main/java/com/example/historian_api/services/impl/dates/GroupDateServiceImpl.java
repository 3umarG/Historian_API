package com.example.historian_api.services.impl.dates;
import com.example.historian_api.dtos.requests.GroupDateRequestDto;
import com.example.historian_api.dtos.responses.GroupDateResponseDto;
import com.example.historian_api.entities.dates.GroupDate;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.mappers.GradeGroupProjectionToGradeGroupMapper;
import com.example.historian_api.mappers.GroupDateToGroupDateResponseDtoMapper;
import com.example.historian_api.repositories.dates.DateRepository;
import com.example.historian_api.repositories.dates.GradeGroupRepository;
import com.example.historian_api.services.base.dates.GroupDateServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class GroupDateServiceImpl implements GroupDateServices {

    private final DateRepository repository;
    private final GradeGroupRepository gradeGroupRepository;
    private final GroupDateToGroupDateResponseDtoMapper mapper;
    private final GradeGroupProjectionToGradeGroupMapper groupProjectionToGradeGroupMapper;

    @Override
    public List<GroupDateResponseDto> getAllGroupDate(Long groupId) {
        var group=gradeGroupRepository.findByGroupIdWithProjection(groupId);
        if(group==null){
            throw new NotFoundResourceException("There is no group with that id !!");
        }
        List<GroupDate>allGroupDate=repository.findAllByGroupId(groupId);
        return allGroupDate
                .stream().map(Gd->mapper.apply(Gd))
                .toList();
    }

    @Override
    public GroupDateResponseDto saveGroupDate(GroupDateRequestDto groupDateRequestDto) {
        var gradeGroup = gradeGroupRepository.findByGroupIdWithProjection(groupDateRequestDto.groupId());
        if(gradeGroup==null){
            throw new NotFoundResourceException("There is no group with that id !!");
        }
        GroupDate groupDate=GroupDate
                .builder()
                .group(groupProjectionToGradeGroupMapper.apply(gradeGroup))
                .lessonDateTime(groupDateRequestDto.lessonDateTime())
                .dayName(groupDateRequestDto.dayName())
                .build();
        repository.save(groupDate);
        return mapper.apply(groupDate);
    }

    @Override
    public GroupDateResponseDto updateGroupDate(Long dateId,String newDayName, String newLessonDateTime) {
        var groupDate = repository.findById(dateId)
                .orElseThrow(() -> new NotFoundResourceException("There is no date with that id !!"));
        groupDate.setLessonDateTime(newLessonDateTime);
        groupDate.setDayName(newDayName);
        var updatedDate=repository.save(groupDate);
        return mapper.apply(updatedDate);
    }

    @Override
    public GroupDateResponseDto removeGroupDate(Long dateId) {
        var groupDate = repository.findById(dateId)
                .orElseThrow(() -> new NotFoundResourceException("There is no group date with that id !!"));
        repository.delete(groupDate);
        return mapper.apply(groupDate);
    }
}
