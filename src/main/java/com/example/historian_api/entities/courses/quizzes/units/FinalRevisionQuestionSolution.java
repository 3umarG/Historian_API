package com.example.historian_api.entities.courses.quizzes.units;

import com.example.historian_api.entities.courses.quizzes.QuestionSolution;
import com.example.historian_api.entities.keys.FinalRevisionQuestionSolutionKey;
import com.example.historian_api.entities.users.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "final_revision_questions_solutions")
public class FinalRevisionQuestionSolution extends QuestionSolution {

    @EmbeddedId
    private FinalRevisionQuestionSolutionKey key;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @MapsId("studentId")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @MapsId("questionId")
    private FinalRevisionQuestion question;

    public FinalRevisionQuestionSolution(
            FinalRevisionQuestionSolutionKey key,
            Student student,
            FinalRevisionQuestion question,
            Integer actualAnswerIndex,
            Integer studentAnswerIndex,
            String actualAnswer,
            String studentAnswer,
            Boolean isStudentSucceeded) {
        super(actualAnswerIndex, studentAnswerIndex, actualAnswer, studentAnswer, isStudentSucceeded);
        this.key = key;
        this.student = student;
        this.question = question;
    }
}
