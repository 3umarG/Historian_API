package com.example.historian_api.services.base.auth;

import com.example.historian_api.entities.users.StudentImage;

import java.util.Optional;

public interface StudentsImageService {

    Optional<StudentImage> findImageByTitle(String title);

    StudentImage insertImage(StudentImage userImage);

    byte[] downloadImage(String title);

}
