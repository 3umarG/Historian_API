package com.example.historian_api.entities.courses;


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
public class LessonQuizResult {

    @EmbeddedId
    private LessonQuizResultKey key;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("lessonId")
    @JoinColumn(name = "lesson_id")
    private UnitLesson lesson;

    private BigDecimal solutionPercentage;

    private BigDecimal takenTimeToSolveInSeconds;

    private BigDecimal totalScore;

    private BigDecimal actualScore;


}
