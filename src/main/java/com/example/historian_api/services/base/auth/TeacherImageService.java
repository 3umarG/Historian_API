package com.example.historian_api.services.base.auth;

import com.example.historian_api.entities.users.TeacherImage;

public interface TeacherImageService {

    TeacherImage insertImage(TeacherImage userImage);

    byte[] downloadImage(String title);


}
