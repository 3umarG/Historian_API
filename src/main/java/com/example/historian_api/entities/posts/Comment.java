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
@Table(name = "posts_comments")
public class Comment {

    /*
  Table "comments" {
      id integer [pk]
      content text
      creationDate date [default: `now()`]
      authorId integer
      postId integer
}
     */

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
    @JoinColumn(name = "postId")
    private Post post;

    public Comment(String content, Student creator, Post post) {
        this.content = content;
        this.creationDate = LocalDate.now();
        this.creator = creator;
        this.post = post;
    }

}
