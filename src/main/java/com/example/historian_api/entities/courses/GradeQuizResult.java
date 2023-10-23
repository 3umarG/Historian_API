package com.example.historian_api.entities.courses;


import com.example.historian_api.entities.keys.GradeQuizResultKey;
import com.example.historian_api.entities.users.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grade_quizzes_results")
@Builder
public class GradeQuizResult {

    @EmbeddedId
    private GradeQuizResultKey key;

    @ManyToOne
    @MapsId("quizId")
    @JoinColumn(name = "quiz_id")
    private GradeQuiz quiz;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    private BigDecimal solutionPercentage;

    private BigDecimal takenTimeToSolveInSeconds;

    private BigDecimal totalScore;

    private BigDecimal actualScore;

}
