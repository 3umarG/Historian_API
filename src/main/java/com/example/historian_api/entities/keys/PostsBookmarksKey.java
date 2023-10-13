package com.example.historian_api.entities.keys;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostsBookmarksKey implements Serializable {
    private Integer postId;
    private Integer studentId;
}
