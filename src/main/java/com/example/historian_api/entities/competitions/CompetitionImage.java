package com.example.historian_api.entities.competitions;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "competitions_images")
public class CompetitionImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;


    private byte[] data;

    public CompetitionImage(String title, byte[] data) {
        this.title = title;
        this.data = data;
    }
}
