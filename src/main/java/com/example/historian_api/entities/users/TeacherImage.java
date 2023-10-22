package com.example.historian_api.entities.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teachers_images")
@Builder
public class TeacherImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;


    private byte[] data;


    public TeacherImage(String title, byte[] data) {
        this.title = title;
        this.data = data;
    }
}
