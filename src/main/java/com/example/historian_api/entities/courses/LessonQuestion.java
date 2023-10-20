package com.example.historian_api.entities.courses;


import com.example.historian_api.entities.converters.StringToListConverter;
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
public class LessonQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private UnitLesson lesson;

    private String question;

    @Convert(converter = StringToListConverter.class)
    private List<String> answers = new ArrayList<>();

    private Integer correctAnswerIndex; // TODO: How teacher will send the correct answer reference ??


    public LessonQuestion(UnitLesson lesson,
                          String question,
                          List<String> answers,
                          Integer correctAnswerIndex) {
        this.lesson = lesson;
        this.question = question;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }
}
