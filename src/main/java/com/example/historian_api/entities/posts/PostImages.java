package com.example.historian_api.entities.posts;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts_images")
public class PostImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private byte[] data;

    @Column(nullable = false)
    private String title;

    private String photoUrl;

    public PostImages(Post post, byte[] data, String title, String photoUrl) {
        this.post = post;
        this.data = data;
        this.title = title;
        this.photoUrl = photoUrl;
    }
}
