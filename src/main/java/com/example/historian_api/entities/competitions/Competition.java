package com.example.historian_api.entities.competitions;


import com.example.historian_api.entities.users.Teacher;
import com.example.historian_api.entities.users.TeacherImage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "competitions")
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    private String photoUrl;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToOne()
    @JoinColumn(name = "photo_id")
    private CompetitionImage competitionImage;

    public Competition(String title, String description, String photoUrl, Teacher teacher, CompetitionImage competitionImage) {
        this.title = title;
        this.competitionImage = competitionImage;
        this.description = description;
        this.photoUrl = photoUrl;
        this.teacher = teacher;
    }
}
