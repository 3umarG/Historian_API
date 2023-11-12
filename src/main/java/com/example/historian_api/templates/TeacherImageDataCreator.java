package com.example.historian_api.templates;

import com.example.historian_api.entities.shared.ImageData;
import com.example.historian_api.entities.users.TeacherImage;
import com.example.historian_api.services.base.auth.TeacherImageService;
import com.example.historian_api.services.impl.auth.TeacherImageServiceImpl;
import com.example.historian_api.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Qualifier("TeacherImageDataCreator")
@RequiredArgsConstructor
public class TeacherImageDataCreator extends ImageDataCreatorTemplate {

    private final TeacherImageService teacherImageService;

    @Override
    protected ImageData insertImageDataToDbWithTitle(MultipartFile photo, String imageTitle) throws IOException {
        return teacherImageService.insertImage(
                new TeacherImage(
                        imageTitle,
                        imageUtils.compressImage(photo.getBytes())
                ));
    }

}
