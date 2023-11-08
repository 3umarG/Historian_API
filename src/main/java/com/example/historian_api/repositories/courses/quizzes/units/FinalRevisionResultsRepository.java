package com.example.historian_api.repositories.courses.quizzes.units;

import com.example.historian_api.entities.courses.quizzes.units.FinalRevisionResult;
import com.example.historian_api.entities.keys.FinalRevisionResultKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinalRevisionResultsRepository extends JpaRepository<FinalRevisionResult, FinalRevisionResultKey> {

    Boolean existsFinalRevisionResultByStudent_IdAndUnit_Id(Integer studentId, Integer unitId);

}