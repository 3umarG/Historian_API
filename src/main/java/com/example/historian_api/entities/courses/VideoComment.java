package com.example.historian_api.entities.courses;


import com.example.historian_api.entities.posts.CommentReply;
import com.example.historian_api.entities.posts.Post;
import com.example.historian_api.entities.users.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "creatorId")
    private Student creator;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private UnitLesson lesson;

    @OneToMany(
            mappedBy = "comment",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<VideoCommentReply> replies = new ArrayList<>();


    public VideoComment(String content,
                        LocalDate creationDate,
                        Student creator,
                        UnitLesson lesson,
                        List<VideoCommentReply> replies) {
        this.content = content;
        this.creationDate = creationDate;
        this.creator = creator;
        this.lesson = lesson;
        this.replies = replies;
    }
}
