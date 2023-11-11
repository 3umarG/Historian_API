package com.example.historian_api.entities.courses;

import com.example.historian_api.entities.courses.quizzes.units.Unit;
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
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(nullable = false,length = 100)
    private String title;


    @ManyToOne
    @JoinColumn(name = "term_id")
    private GradeTerm term;


    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Unit> units = new ArrayList<>();

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<EnrollmentCourse> enrollmentCourses = new ArrayList<>();
    public Course(String title, GradeTerm term) {
        this.title = title;
        this.term = term;
    }
}
