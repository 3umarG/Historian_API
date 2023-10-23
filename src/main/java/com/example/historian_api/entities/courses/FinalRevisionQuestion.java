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

    private Integer correctAnswerIndex; // TODO: How teacher will send the correct answer reference ??

    private Boolean isCheckedAnswer;

    public FinalRevisionQuestion(Unit unit,
                                 String question,
                                 List<String> answers,
                                 Integer correctAnswerIndex,
                                 Boolean isCheckedAnswer) {
        this.unit = unit;
        this.isCheckedAnswer = isCheckedAnswer;
        this.question = question;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }
}
