package com.example.historian_api.services.base.dates;
import com.example.historian_api.dtos.requests.GradeGroupRequestDto;
import com.example.historian_api.dtos.responses.GradeGroupResponseDto;
import com.example.historian_api.entities.projections.GradeGroupProjection;
import com.example.historian_api.exceptions.NotFoundResourceException;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface GradeGroupsServices {

     GradeGroupResponseDto saveGradeGroup(@NotNull GradeGroupRequestDto dto) throws NotFoundResourceException;
     GradeGroupResponseDto updateGroupTitle(@NotNull Long groupId,@NotNull String newTitle) throws NotFoundResourceException;
     List<GradeGroupResponseDto>getAllGroups();
     GradeGroupResponseDto getGroupById(Long groupId);
     List<GradeGroupResponseDto>getGroupsByGradeId(Integer gradeId);
}
