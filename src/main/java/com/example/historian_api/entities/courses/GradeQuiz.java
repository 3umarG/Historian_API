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
@Table(name = "grade_quizzes")
public class GradeQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private StudentGrade grade;

    @OneToMany(
            mappedBy = "quiz",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<GradeQuizQuestion> questions = new ArrayList<>();

    @OneToMany(
            mappedBy = "quiz",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<GradeQuizResult> results = new ArrayList<>();

    public GradeQuiz(String title, String description, StudentGrade grade) {
        this.title = title;
        this.description = description;
        this.grade = grade;
    }
}
