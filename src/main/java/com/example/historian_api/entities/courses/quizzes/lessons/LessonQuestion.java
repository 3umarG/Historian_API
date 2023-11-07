package com.example.historian_api.entities.courses.quizzes.lessons;


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
@Table(name = "lessons_questions")
public class LessonQuestion extends Question {

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private UnitLesson lesson;


    @ElementCollection
    @CollectionTable(name = "lessons_question_answers", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "answer")
    private List<String> answers = new ArrayList<>();


    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<LessonQuestionSolution> solutions = new ArrayList<>();

    public LessonQuestion(UnitLesson lesson,
                          String question,
                          List<String> answers,
                          Integer correctAnswerIndex,
                          Boolean isCheckedAnswer,
                          String correctAnswerDescription,
                          String photoUrl,
                          QuestionImage questionImage) {
        super(question, correctAnswerIndex, isCheckedAnswer, correctAnswerDescription, questionImage, photoUrl);
        this.lesson = lesson;
        this.answers = answers;
    }
}
