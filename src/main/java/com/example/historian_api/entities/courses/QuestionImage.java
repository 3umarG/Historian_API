package com.example.historian_api.entities.courses;

import com.example.historian_api.entities.shared.ImageData;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "questions_images")
public class QuestionImage extends ImageData {

    public QuestionImage(String title, byte[] data) {
        super(title, data);
    }

    public QuestionImage() {
        super();
    }
}
