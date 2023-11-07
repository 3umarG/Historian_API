package com.example.historian_api.entities.courses.quizzes.units;


import com.example.historian_api.entities.converters.StringToListConverter;
import com.example.historian_api.entities.courses.QuestionImage;
import com.example.historian_api.entities.courses.quizzes.Question;
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
public class FinalRevisionQuestion extends Question {

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;


    @ElementCollection
    @CollectionTable(name = "final_revision_question_answers", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "answer")
    private List<String> answers = new ArrayList<>();


    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
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
        super(question, correctAnswerIndex, isCheckedAnswer, correctAnswerDescription, questionImage, photoUrl);
        this.unit = unit;
        this.answers = answers;
    }
}
