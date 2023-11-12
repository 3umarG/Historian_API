package com.example.historian_api.utils.repositories_utils.units_final_revision;

import com.example.historian_api.entities.courses.quizzes.units.Unit;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.courses.UnitsRepository;
import com.example.historian_api.utils.constants.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnitsRepositoryUtils {
    private final UnitsRepository unitsRepository;

    public Boolean isNotFoundUnitById(Integer unitId){
        return !unitsRepository.existsById(unitId);
    }

    public Unit getUnitByIdOrThrowNotFound(Integer unitId){
        return unitsRepository.findById(unitId)
                .orElseThrow(() -> new NotFoundResourceException(ExceptionMessages.getNotFoundResourceMessage("Unit")));
    }

}
