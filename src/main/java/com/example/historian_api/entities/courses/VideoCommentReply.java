package com.example.historian_api.entities.courses;


import com.example.historian_api.entities.posts.Comment;
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
@Table(name = "video_comments_replies")
public class VideoCommentReply {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private VideoComment comment;

    public VideoCommentReply(String content,
                             LocalDateTime createdAt,
                             Teacher teacher,
                             Student student,
                             VideoComment comment) {
        this.content = content;
        this.createdAt = createdAt;
        this.teacher = teacher;
        this.student = student;
        this.comment = comment;
    }
}
