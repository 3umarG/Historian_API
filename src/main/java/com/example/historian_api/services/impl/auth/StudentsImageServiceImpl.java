package com.example.historian_api.services.impl.auth;

import com.example.historian_api.entities.users.StudentImage;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.StudentImageRepository;
import com.example.historian_api.services.base.auth.StudentsImageService;
import com.example.historian_api.utils.ImageUtils;
import com.example.historian_api.utils.constants.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentsImageServiceImpl implements StudentsImageService {

    private final StudentImageRepository studentImageRepository;
    private final ImageUtils imageUtils;


    @Override
    public Optional<StudentImage> findImageByTitle(String title) {
        return studentImageRepository.findByTitle(title);
    }

    @Override
    public StudentImage insertImage(StudentImage userImage) {
        return studentImageRepository.save(userImage);
    }

    @Override
    public byte[] downloadImage(String title) {
        StudentImage dbStudentImage = studentImageRepository
                .findByTitle(title)
                .orElseThrow(() -> new NotFoundResourceException(ExceptionMessages.NOT_FOUND_EXCEPTION_MSG));

        return imageUtils.decompressImage(dbStudentImage.getData());
    }
}
