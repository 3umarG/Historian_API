package com.example.historian_api.templates;

import com.example.historian_api.entities.courses.QuestionImage;
import com.example.historian_api.entities.shared.ImageData;
import com.example.historian_api.services.impl.images_data.QuestionsImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Qualifier("QuestionImageDataCreator")
@RequiredArgsConstructor
public class QuestionImageDataCreator extends ImageDataCreatorTemplate {

    private final QuestionsImagesService questionsImagesService;

    @Override
    protected ImageData insertImageDataToDbWithTitle(MultipartFile photo, String imageTitle) throws IOException {
        return questionsImagesService.insertImage(
                new QuestionImage(
                        imageTitle,
                        imageUtils.compressImage(photo.getBytes())
                ));
    }
}
