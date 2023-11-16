package com.example.historian_api.entities.courses.quizzes.grades;


import com.example.historian_api.entities.courses.GradeSemester;
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

    private Boolean isFinal;

    private Boolean isHidden;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private GradeSemester semester;

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

    public GradeQuiz(String title, String description, GradeSemester semester, Boolean isFinal, Boolean isHidden) {
        this.title = title;
        this.isHidden = isHidden;
        this.isFinal = isFinal;
        this.description = description;
        this.semester = semester;
    }
}
