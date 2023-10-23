package com.example.historian_api.entities.courses;


import com.example.historian_api.entities.converters.StringToListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grade_quizzes_questions")
public class GradeQuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private GradeQuiz quiz;

    private String question;

    @Convert(converter = StringToListConverter.class)
    private List<String> answers = new ArrayList<>();

    private Integer correctAnswerIndex;

    private Boolean isCheckedAnswer;

    public GradeQuizQuestion(
            GradeQuiz quiz,
            String question,
            List<String> answers,
            Integer correctAnswerIndex,
            Boolean isCheckedAnswer
    ) {
        this.quiz = quiz;
        this.question = question;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
        this.isCheckedAnswer = isCheckedAnswer;
    }
}
