package com.example.historian_api.entities.posts;

import com.example.historian_api.entities.users.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts_likes")
public class Like {

    /*
    Table "likes" {
      id integer [pk]
      authorId integer
      creationDate date [default: `now()`]
      postId integer
    }

     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "creatorId")
    private Student creator;

    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;


    public Like(Student creator, Post post) {
        this.creator = creator;
        this.creationDate = LocalDate.now();
        this.post = post;
    }
}
