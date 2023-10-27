package com.example.historian_api.services.impl.courses;

import com.example.historian_api.dtos.responses.UnitResponseDto;
import com.example.historian_api.repositories.courses.UnitsRepository;
import com.example.historian_api.services.base.courses.UnitsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitsServiceImpl implements UnitsService {

    private final UnitsRepository unitsRepository;

    @Override
    public List<UnitResponseDto> getAllUnitsByCourseId(Integer courseId) {
        var unitsProjection = unitsRepository.getAllUnitsDetailsByCourseId(courseId);
        return unitsProjection.stream().map(unit -> new UnitResponseDto(
                        unit.getId(),
                        unit.getTitle(),
                        unit.getContent(),
                        unit.getContentCount(),
                        unit.getContentCount()
                )).toList();
    }
}
