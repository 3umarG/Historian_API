package com.example.historian_api.entities.courses.quizzes.grades;


import com.example.historian_api.entities.courses.quizzes.QuizResult;
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
public class GradeQuizResult extends QuizResult {

    @EmbeddedId
    private GradeQuizResultKey key;

    @ManyToOne
    @MapsId("quizId")
    @JoinColumn(name = "quiz_id")
    private GradeQuiz quiz;

    public GradeQuizResult(
            GradeQuizResultKey key,
            GradeQuiz quiz,
            Student student,
            BigDecimal solutionPercentage,
            BigDecimal takenTimeToSolveInSeconds,
            BigDecimal totalScore,
            BigDecimal actualScore) {
        super(student, solutionPercentage, takenTimeToSolveInSeconds, totalScore, actualScore);
        this.key = key;
        this.quiz = quiz;
    }
}
