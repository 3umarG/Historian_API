package com.example.historian_api.entities.courses.quizzes.grades;

import com.example.historian_api.entities.keys.GradeQuizQuestionSolutionKey;
import com.example.historian_api.entities.users.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "grade_quizzes_questions_solutions")
@AllArgsConstructor
@NoArgsConstructor
public class GradeQuizQuestionSolution {

    @EmbeddedId
    private GradeQuizQuestionSolutionKey key;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("questionId")
    @JoinColumn(name = "question_id")
    private GradeQuizQuestion question;

    private Integer actualAnswerIndex;

    private Integer studentAnswerIndex;

    private String actualAnswer;

    private String studentAnswer;

    private Boolean isStudentSucceeded;
}
