package com.example.historian_api.entities.dates;

import com.example.historian_api.entities.courses.StudentGrade;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Setter
@Table(name = "grades_groups")
public class GradeGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private StudentGrade grade;

    @OneToMany(
            mappedBy = "group",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<GroupDate> dates = new ArrayList<>();

    public GradeGroup(String title, StudentGrade grade) {
        this.title = title;
        this.grade = grade;
    }
}
