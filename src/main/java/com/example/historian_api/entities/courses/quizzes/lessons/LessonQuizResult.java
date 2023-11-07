package com.example.historian_api.entities.courses.quizzes.lessons;


import com.example.historian_api.entities.courses.quizzes.QuizResult;
import com.example.historian_api.entities.keys.LessonQuizResultKey;
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
@Table(name = "lessons_quizzes_results")
public class LessonQuizResult extends QuizResult {

    @EmbeddedId
    private LessonQuizResultKey key;


    @ManyToOne
    @MapsId("lessonId")
    @JoinColumn(name = "lesson_id")
    private UnitLesson lesson;


    public LessonQuizResult(
            LessonQuizResultKey key,
            Student student,
            UnitLesson lesson,
            BigDecimal solutionPercentage,
            BigDecimal takenTimeToSolveInSeconds,
            BigDecimal totalScore,
            BigDecimal actualScore) {
        super(student, solutionPercentage, takenTimeToSolveInSeconds, totalScore, actualScore);
        this.key = key;
        this.lesson = lesson;
    }
}
