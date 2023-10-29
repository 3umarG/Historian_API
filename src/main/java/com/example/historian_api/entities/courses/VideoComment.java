package com.example.historian_api.entities.courses;


import com.example.historian_api.entities.courses.quizzes.lessons.UnitLesson;
import com.example.historian_api.entities.users.Student;
import com.example.historian_api.entities.users.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "videos_comments")
public class VideoComment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private UnitLesson lesson;



    public VideoComment(String content,
                        LocalDateTime creationDate,
                        Student student,
                        UnitLesson lesson) {
        this.content = content;
        this.creationDate = creationDate;
        this.student = student;
        this.lesson = lesson;
    }

    public VideoComment(String content,
                        LocalDateTime creationDate,
                        Teacher teacher,
                        UnitLesson lesson) {
        this.content = content;
        this.creationDate = creationDate;
        this.teacher = teacher;
        this.lesson = lesson;
    }
}
