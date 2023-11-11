package com.example.historian_api.entities.courses;

import com.example.historian_api.entities.courses.quizzes.grades.GradeQuiz;
import com.example.historian_api.entities.dates.GradeGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "students_grades")
public class StudentGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @JsonIgnore
    @OneToMany(
            mappedBy = "grade",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<GradeTerm> terms = new ArrayList<>();

    @JsonIgnore
    @OneToMany(
            mappedBy = "grade",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<GradeQuiz> quizzes = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "grade", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<GradeGroup> gradeGroupList = new ArrayList<>();


    public StudentGrade(String name) {
        this.name = name;
    }
}
