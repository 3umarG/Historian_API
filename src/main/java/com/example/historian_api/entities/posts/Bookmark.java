package com.example.historian_api.entities.posts;


import com.example.historian_api.entities.keys.PostsBookmarksKey;
import com.example.historian_api.entities.users.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookmarks")
public class Bookmark {

    @EmbeddedId
    private PostsBookmarksKey key;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;


    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDate savedOn;

    public Bookmark(Student student, Post post, LocalDate savedOn) {
        this.student = student;
        this.post = post;
        this.savedOn = savedOn;
    }
}
