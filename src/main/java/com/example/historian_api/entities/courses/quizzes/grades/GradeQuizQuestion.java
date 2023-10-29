package com.example.historian_api.entities.courses.quizzes.grades;


import com.example.historian_api.entities.converters.StringToListConverter;
import com.example.historian_api.entities.courses.QuestionImage;
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
public class GradeQuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private GradeQuiz quiz;

    private String question;

    @ElementCollection
    @CollectionTable(name="grade_quiz_question_answers", joinColumns=@JoinColumn(name="question_id"))
    @Column(name="answer")
    private List<String> answers = new ArrayList<>();

    private Integer correctAnswerIndex;

    private Boolean isCheckedAnswer;

    private String correctAnswerDescription;

    @OneToOne
    @JoinColumn(name = "question_image_id")
    private QuestionImage questionImage;

    private String photoUrl;

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
        this.photoUrl = photoUrl;
        this.questionImage = questionImage;
        this.correctAnswerDescription = correctAnswerDescription;
        this.quiz = quiz;
        this.question = question;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
        this.isCheckedAnswer = isCheckedAnswer;
    }
}
