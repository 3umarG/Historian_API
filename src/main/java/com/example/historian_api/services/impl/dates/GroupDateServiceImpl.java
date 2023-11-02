package com.example.historian_api.services.impl.dates;
import com.example.historian_api.dtos.requests.GroupDateRequestDto;
import com.example.historian_api.dtos.responses.GroupDateResponseDto;
import com.example.historian_api.entities.dates.GroupDate;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.mappers.GroupDateToGroupDateResponseDtoMapper;
import com.example.historian_api.repositories.dates.DateRepository;
import com.example.historian_api.repositories.dates.GradeGroupRepository;
import com.example.historian_api.services.base.dates.GroupDateServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupDateServiceImpl implements GroupDateServices {

    private final DateRepository groupsDatesRepository;
    private final GradeGroupRepository groupsRepository;
    private final GroupDateToGroupDateResponseDtoMapper groupDateResponseDtoMapper;


    @Override
    public List<GroupDateResponseDto> getAllGroupDate(Long groupId) {
        groupsRepository.findById(groupId).orElseThrow(
                () -> new NotFoundResourceException("There is no group with that id !!")
        );

        List<GroupDate> allGroupDate = groupsDatesRepository.findAllByGroupId(groupId);

        return allGroupDate
                .stream().map(groupDateResponseDtoMapper)
                .toList();
    }

    @Override
    public GroupDateResponseDto saveGroupDate(GroupDateRequestDto groupDateRequestDto) {
        var gradeGroup = groupsRepository.findById(groupDateRequestDto.groupId())
                .orElseThrow(() -> new NotFoundResourceException("There is no grade group with that id !!"));
        GroupDate groupDate = GroupDate
                .builder()
                .group(gradeGroup)
                .lessonDateTime(groupDateRequestDto.lessonDateTime())
                .dayName(groupDateRequestDto.dayName())
                .build();
        groupsDatesRepository.save(groupDate);
        return groupDateResponseDtoMapper.apply(groupDate);
    }

    @Override
    public GroupDateResponseDto updateGroupDate(Long dateId, String newDayName, String newLessonDateTime) {
        var groupDate = groupsDatesRepository.findById(dateId)
                .orElseThrow(() -> new NotFoundResourceException("There is no group date with that id !!"));
        groupDate.setLessonDateTime(newLessonDateTime);
        groupDate.setDayName(newDayName);
        var updatedDate = groupsDatesRepository.save(groupDate);
        return groupDateResponseDtoMapper.apply(updatedDate);
    }

    @Override
    public GroupDateResponseDto removeGroupDate(Long dateId) {
        var groupDate = groupsDatesRepository.findById(dateId)
                .orElseThrow(() -> new NotFoundResourceException("There is no group date with that id !!"));
        groupsDatesRepository.delete(groupDate);
        return groupDateResponseDtoMapper.apply(groupDate);
    }
}
