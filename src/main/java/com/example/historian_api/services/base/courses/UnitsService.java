package com.example.historian_api.services.base.courses;

import com.example.historian_api.dtos.responses.UnitResponseDto;

import java.util.List;

public interface UnitsService {
    List<UnitResponseDto> getAllUnitsByCourseId(Integer courseId);
}
