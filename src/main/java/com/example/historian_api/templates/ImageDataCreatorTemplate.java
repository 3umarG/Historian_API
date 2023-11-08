package com.example.historian_api.templates;

import com.example.historian_api.dtos.responses.ImageDataWithTitleDto;
import com.example.historian_api.entities.shared.ImageData;
import com.example.historian_api.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public abstract class ImageDataCreatorTemplate {

    @Autowired
    protected ImageUtils imageUtils;

    public ImageDataWithTitleDto generateImageDataWithTitle(MultipartFile photo, String imagePath) throws IOException {

        if (photo != null) {
            String imageTitle = ImageUtils.generateUniqueImageTitle(photo.getName());

            ImageData imageData = insertImageDataToDbWithTitle(photo, imageTitle);
            String photoUrl = imageUtils.generateImagePath(imagePath, imageTitle);

            return new ImageDataWithTitleDto(imageData, photoUrl);
        }

        return new ImageDataWithTitleDto(null, null);
    }

    protected abstract ImageData insertImageDataToDbWithTitle(MultipartFile photo, String imageTitle) throws IOException;

}
