package com.example.historian_api.services.impl.images_data;

import com.example.historian_api.entities.courses.QuestionImage;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.courses.QuestionsImagesRepository;
import com.example.historian_api.utils.ImageUtils;
import com.example.historian_api.utils.constants.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionsImagesServiceImpl implements QuestionsImagesService {

    private final QuestionsImagesRepository questionsImagesRepository;
    private final ImageUtils imageUtils;

    @Override
    public QuestionImage insertImage(QuestionImage image) {
        return questionsImagesRepository.save(image);
    }

    @Override
    public byte[] downloadImage(String title) {
        QuestionImage dbTeacherImage = questionsImagesRepository
                .findByTitle(title)
                .orElseThrow(() -> new NotFoundResourceException(ExceptionMessages.NOT_FOUND_EXCEPTION_MSG));

        return imageUtils.decompressImage(dbTeacherImage.getData());

    }
}
