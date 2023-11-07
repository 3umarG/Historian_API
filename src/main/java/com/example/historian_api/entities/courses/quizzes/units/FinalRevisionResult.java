package com.example.historian_api.entities.courses.quizzes.units;


import com.example.historian_api.entities.courses.quizzes.QuizResult;
import com.example.historian_api.entities.keys.FinalRevisionResultKey;
import com.example.historian_api.entities.users.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "final_revisions_results")
public class FinalRevisionResult extends QuizResult {

    @EmbeddedId
    private FinalRevisionResultKey key;

    @ManyToOne
    @MapsId("unitId")
    @JoinColumn(name = "unit_id")
    private Unit unit;

    public FinalRevisionResult(
            Student student,
            BigDecimal solutionPercentage,
            BigDecimal takenTimeToSolveInSeconds,
            BigDecimal totalScore,
            BigDecimal actualScore,
            FinalRevisionResultKey key,
            Unit unit) {
        super(student, solutionPercentage, takenTimeToSolveInSeconds, totalScore, actualScore);
        this.key = key;
        this.unit = unit;
    }
}
