package com.example.historian_api.entities.courses;


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
@Table(name = "final_revision_exams")
public class FinalRevisionExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "unit_id")
    private CourseUnit unit;

    private String question;

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    private List<String> answers = new ArrayList<>();

    private Integer correctAnswerIndex; // TODO: How teacher will send the correct answer reference ??

    @OneToMany(
            mappedBy = "revisionExam",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<FinalRevisionResult> results = new ArrayList<>();


    public FinalRevisionExam(CourseUnit unit,
                             String question,
                             List<String> answers,
                             Integer correctAnswerIndex) {
        this.unit = unit;
        this.question = question;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }
}
