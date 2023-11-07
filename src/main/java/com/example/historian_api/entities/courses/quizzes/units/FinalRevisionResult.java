package com.example.historian_api.entities.courses.quizzes.units;


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
public class FinalRevisionResult {

    @EmbeddedId
    private FinalRevisionResultKey key;

    @ManyToOne
    @MapsId("unitId")
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    private BigDecimal solutionPercentage;

    private BigDecimal takenTimeToSolveInSeconds;

    private BigDecimal totalScore;

    private BigDecimal actualScore;

}
