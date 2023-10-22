package com.example.historian_api.entities.posts;

import com.example.historian_api.entities.users.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {


  /*
  Table "posts" {
      id integer [pk]
      title varchar(100) [not null]
      content text
      authorId integer
    }
   */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100,nullable = true)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDate creationDate;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "teacherId")
    private Teacher teacher;


    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "post",
            fetch = FetchType.LAZY
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Comment> comments = new ArrayList<>();


    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "post",
            fetch = FetchType.LAZY
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Like> likes = new ArrayList<>();


    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "post",
            fetch = FetchType.EAGER
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PostImages> postImages =new ArrayList<>();

  public Post(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public Post(String title, String content, Teacher teacher) {
    this.title = title;
    this.content = content;
    this.teacher = teacher;
  }
}
