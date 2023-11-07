package com.example.historian_api.entities.courses.quizzes.lessons;

import com.example.historian_api.entities.courses.quizzes.QuestionSolution;
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
public class LessonQuestionSolution extends QuestionSolution {

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

    public LessonQuestionSolution(
            LessonQuestionSolutionKey key,
            Student student,
            LessonQuestion question,
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
