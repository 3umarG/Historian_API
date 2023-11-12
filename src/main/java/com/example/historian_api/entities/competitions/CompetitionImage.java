package com.example.historian_api.entities.competitions;


import com.example.historian_api.entities.shared.ImageData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Entity
@Table(name = "competitions_images")
public class CompetitionImage extends ImageData {


    public CompetitionImage(String title, byte[] data) {
       super(title, data);
    }
}
