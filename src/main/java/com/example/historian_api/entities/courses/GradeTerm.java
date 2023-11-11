package com.example.historian_api.entities.courses;

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
@Table(name = "grades_terms")
public class GradeTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private StudentGrade grade;

    @OneToMany(
            mappedBy = "term",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Course> courses = new ArrayList<>();

    public GradeTerm(String name, StudentGrade grade) {
        this.name = name;
        this.grade = grade;
    }
}
