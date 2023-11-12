package com.example.historian_api.entities.users;

import com.example.historian_api.entities.shared.ImageData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Table(name = "teachers_images")
@Builder
public class TeacherImage extends ImageData {

    public TeacherImage(String title, byte[] data) {
       super(title, data);
    }
}
