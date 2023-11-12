package com.example.historian_api.services.base.images;

import com.example.historian_api.entities.competitions.CompetitionImage;
import com.example.historian_api.entities.shared.ImageData;

public interface ImagesService<T extends ImageData> {
    T insertImage(T image);

    byte[] downloadImage(String title);
}
