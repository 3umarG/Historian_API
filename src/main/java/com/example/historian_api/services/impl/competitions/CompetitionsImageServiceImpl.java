package com.example.historian_api.services.impl.competitions;

import com.example.historian_api.entities.competitions.CompetitionImage;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.competitions.CompetitionsImagesRepository;
import com.example.historian_api.services.base.competitions.CompetitionsImagesService;
import com.example.historian_api.utils.ImageUtils;
import com.example.historian_api.utils.constants.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetitionsImageServiceImpl implements CompetitionsImagesService {

    private final CompetitionsImagesRepository competitionsImagesRepository;
    private final ImageUtils imageUtils;

    @Override
    public CompetitionImage insertImage(CompetitionImage competitionImage) {
        return competitionsImagesRepository.save(competitionImage);
    }

    @Override
    public byte[] downloadImage(String title) {
        CompetitionImage dbStudentImage = competitionsImagesRepository
                .findByTitle(title)
                .orElseThrow(() -> new NotFoundResourceException(ExceptionMessages.NOT_FOUND_EXCEPTION_MSG));

        return imageUtils.decompressImage(dbStudentImage.getData());
    }
}
