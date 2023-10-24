package com.example.historian_api.services.base.competitions;

import com.example.historian_api.entities.competitions.CompetitionImage;
import com.example.historian_api.entities.users.TeacherImage;

public interface CompetitionsImagesService {
    CompetitionImage insertImage(CompetitionImage userImage);

    byte[] downloadImage(String title);
}
