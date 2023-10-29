package com.example.historian_api.entities.courses.quizzes.units;


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
@Table(name = "final_revision_questions")
public class FinalRevisionQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    private String question;

    @Convert(converter = StringToListConverter.class)
    private List<String> answers = new ArrayList<>();

    private Integer correctAnswerIndex;

    private Boolean isCheckedAnswer;

    private String correctAnswerDescription;

    @OneToOne
    @JoinColumn(name = "question_image_id")
    private QuestionImage questionImage;

    private String photoUrl;

    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<FinalRevisionQuestionSolution> solutions = new ArrayList<>();

    public FinalRevisionQuestion(Unit unit,
                                 String question,
                                 List<String> answers,
                                 Integer correctAnswerIndex,
                                 Boolean isCheckedAnswer,
                                 String correctAnswerDescription,
                                 QuestionImage questionImage,
                                 String photoUrl) {
        this.unit = unit;
        this.correctAnswerDescription = correctAnswerDescription;
        this.questionImage = questionImage;
        this.photoUrl = photoUrl;
        this.isCheckedAnswer = isCheckedAnswer;
        this.question = question;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }
}
