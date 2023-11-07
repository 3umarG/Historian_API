package com.example.historian_api.entities.courses.quizzes.grades;


import com.example.historian_api.entities.courses.QuestionImage;
import com.example.historian_api.entities.courses.quizzes.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grade_quizzes_questions")
public class GradeQuizQuestion extends Question {


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private GradeQuiz quiz;


    @ElementCollection
    @CollectionTable(name="grade_quiz_question_answers", joinColumns=@JoinColumn(name="question_id"))
    @Column(name="answer")
    private List<String> answers = new ArrayList<>();


    @JsonIgnore
    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<GradeQuizQuestionSolution> solutions = new ArrayList<>();

    public GradeQuizQuestion(
            GradeQuiz quiz,
            String question,
            List<String> answers,
            Integer correctAnswerIndex,
            Boolean isCheckedAnswer,
            String correctAnswerDescription,
            QuestionImage questionImage,
            String photoUrl
    ) {
        super(question,correctAnswerIndex,isCheckedAnswer,correctAnswerDescription,questionImage,photoUrl);
        this.quiz = quiz;
        this.answers = answers;
    }
}
