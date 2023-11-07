package com.example.historian_api.entities.courses.quizzes.grades;

import com.example.historian_api.entities.courses.quizzes.QuestionSolution;
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
public class GradeQuizQuestionSolution extends QuestionSolution {

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

    public GradeQuizQuestionSolution(
            GradeQuizQuestionSolutionKey key,
            Student student,
            GradeQuizQuestion question,
            Integer actualAnswerIndex,
            Integer studentAnswerIndex,
            String actualAnswer,
            String studentAnswer,
            Boolean isStudentSucceeded
            ) {
        super(actualAnswerIndex, studentAnswerIndex, actualAnswer, studentAnswer, isStudentSucceeded);
        this.key = key;
        this.student = student;
        this.question = question;
    }
}
