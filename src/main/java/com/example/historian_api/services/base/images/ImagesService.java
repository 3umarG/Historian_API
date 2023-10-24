package com.example.historian_api.services.base.images;

import com.example.historian_api.entities.competitions.CompetitionImage;

public interface ImagesService<T> {
    T insertImage(T image);

    byte[] downloadImage(String title);
}
