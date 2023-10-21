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
@Table(name = "courses_units")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100,nullable = false)
    private String title;

    @Column(length = 150,nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(
            mappedBy = "unit"
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<FinalRevisionQuestion> revisionQuestions = new ArrayList<>();

    @OneToMany(
            mappedBy = "unit",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<UnitLesson> lessons = new ArrayList<>();

    @OneToMany(
            mappedBy = "unit",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<FinalRevisionResult> revisionResults = new ArrayList<>();

    public Unit(String title, String content, Course course) {
        this.title = title;
        this.content = content;
        this.course = course;
    }
}
