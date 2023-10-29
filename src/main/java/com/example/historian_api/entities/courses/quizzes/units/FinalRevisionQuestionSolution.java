package com.example.historian_api.entities.courses.quizzes.units;

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
public class FinalRevisionQuestionSolution {

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

    private Integer actualAnswerIndex;

    private Integer studentAnswerIndex;

    private String actualAnswer;

    private String studentAnswer;

    private Boolean isStudentSucceeded;
}
