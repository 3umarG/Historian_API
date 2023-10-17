package com.example.historian_api.entities.posts;


import com.example.historian_api.entities.users.Student;
import com.example.historian_api.entities.users.Teacher;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts_comments_replies")
public class CommentReply {

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
    private Comment comment;

    public CommentReply(String content, LocalDateTime createdAt, Teacher teacher, Comment comment) {
        this.content = content;
        this.createdAt = createdAt;
        this.teacher = teacher;
        this.comment = comment;
    }

    public CommentReply(String content, LocalDateTime createdAt, Student student, Comment comment) {
        this.content = content;
        this.createdAt = createdAt;
        this.student = student;
        this.comment = comment;
    }
}
