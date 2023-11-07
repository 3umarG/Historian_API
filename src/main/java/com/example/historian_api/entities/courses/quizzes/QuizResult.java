package com.example.historian_api.entities.courses.quizzes;

import com.example.historian_api.entities.users.Student;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@MappedSuperclass
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuizResult {

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    private BigDecimal solutionPercentage;

    private BigDecimal takenTimeToSolveInSeconds;

    private BigDecimal totalScore;

    private BigDecimal actualScore;

}
