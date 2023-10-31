package com.example.historian_api.mappers;

import com.example.historian_api.dtos.responses.GroupDateResponseDto;
import com.example.historian_api.entities.dates.GroupDate;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class GroupDateToGroupDateResponseDtoMapper implements Function<GroupDate, GroupDateResponseDto> {
    @Override
    public GroupDateResponseDto apply(GroupDate groupDate) {
        Map<String,String> map=new HashMap<>();
        map.put("dayName",groupDate.getDayName());
        map.put("lessonDateTime",groupDate.getLessonDateTime());
        return GroupDateResponseDto
                .builder()
                .dateId(groupDate.getId())
                .groupId(groupDate.getGroup().getId())
                .dayName(groupDate.getDayName())
                .lessonDateTime(groupDate.getLessonDateTime())
                .build();
    }
}
