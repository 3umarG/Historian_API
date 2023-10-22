package com.example.historian_api.services.impl.auth;

import com.example.historian_api.entities.users.TeacherImage;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.users.TeacherImagesRepository;
import com.example.historian_api.services.base.auth.TeacherImageService;
import com.example.historian_api.utils.ImageUtils;
import com.example.historian_api.utils.constants.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherImageServiceImpl implements TeacherImageService {

    private final TeacherImagesRepository teacherImagesRepository;
    private final ImageUtils imageUtils;

    @Override
    public TeacherImage insertImage(TeacherImage userImage) {
        return teacherImagesRepository.save(userImage);
    }

    @Override
    public byte[] downloadImage(String title) {
        TeacherImage dbTeacherImage = teacherImagesRepository
                .findByTitle(title)
                .orElseThrow(() -> new NotFoundResourceException(ExceptionMessages.NOT_FOUND_EXCEPTION_MSG));

        return imageUtils.decompressImage(dbTeacherImage.getData());

    }
}
