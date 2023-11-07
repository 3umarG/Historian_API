package com.example.historian_api.entities.courses.quizzes;

import com.example.historian_api.entities.courses.QuestionImage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String question;

    private Integer correctAnswerIndex;

    private Boolean isCheckedAnswer;

    private String correctAnswerDescription;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "question_image_id")
    private QuestionImage questionImage;

    private String photoUrl;

    public Question(String question,
                    Integer correctAnswerIndex,
                    Boolean isCheckedAnswer,
                    String correctAnswerDescription,
                    QuestionImage questionImage,
                    String photoUrl) {
        this.question = question;
        this.correctAnswerIndex = correctAnswerIndex;
        this.isCheckedAnswer = isCheckedAnswer;
        this.correctAnswerDescription = correctAnswerDescription;
        this.questionImage = questionImage;
        this.photoUrl = photoUrl;
    }
}
