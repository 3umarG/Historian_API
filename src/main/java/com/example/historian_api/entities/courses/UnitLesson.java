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
@Table(name = "units_lessons")
public class UnitLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 150, nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    private String videoUrl;

    private String videoDescription;

    private boolean isFree;

    @OneToMany(
            mappedBy = "lesson",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<LessonQuestion> questions = new ArrayList<>();

    @OneToMany(
            mappedBy = "lesson",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<LessonQuizResult> results = new ArrayList<>();


    @OneToMany(
            mappedBy = "lesson",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<VideoComment> comments = new ArrayList<>();


    public UnitLesson(String title,
                      String content,
                      Unit unit,
                      String videoUrl,
                      String videoDescription,
                      boolean isFree) {
        this.title = title;
        this.content = content;
        this.unit = unit;
        this.videoUrl = videoUrl;
        this.isFree = isFree;
        this.videoDescription = videoDescription;
    }
}
