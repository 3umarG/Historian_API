package com.example.historian_api.entities.courses.quizzes.lessons;

import com.example.historian_api.entities.keys.LessonQuestionSolutionKey;
import com.example.historian_api.entities.users.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "lessons_questions_solutions")
public class LessonQuestionSolution {

    @EmbeddedId
    private LessonQuestionSolutionKey key;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("questionId")
    @JoinColumn(name = "question_id")
    private LessonQuestion question;

    private Integer actualAnswerIndex;

    private Integer studentAnswerIndex;

    private String actualAnswer;

    private String studentAnswer;

    private Boolean isStudentSucceeded;
}
