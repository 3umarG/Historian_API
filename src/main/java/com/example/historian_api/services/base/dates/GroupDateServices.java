package com.example.historian_api.services.base.dates;

import com.example.historian_api.dtos.requests.GroupDateRequestDto;
import com.example.historian_api.dtos.responses.GroupDateResponseDto;

import java.util.List;

public interface GroupDateServices {

    List<GroupDateResponseDto> getAllGroupDate(Long groupId);

    GroupDateResponseDto saveGroupDate(GroupDateRequestDto groupDateRequestDto);

    GroupDateResponseDto updateGroupDate(Long dateId, String newDayName, String newLessonDateTime);

    GroupDateResponseDto removeGroupDate(Long groupId);
}
