package com.example.historian_api.services.impl.competitions;

import com.example.historian_api.dtos.requests.CompetitionRequestDto;
import com.example.historian_api.dtos.responses.CompetitionResponseDto;
import com.example.historian_api.entities.competitions.Competition;
import com.example.historian_api.entities.competitions.CompetitionImage;
import com.example.historian_api.entities.users.Teacher;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.competitions.CompetitionsRepository;
import com.example.historian_api.repositories.users.TeachersRepository;
import com.example.historian_api.services.base.competitions.CompetitionsImagesService;
import com.example.historian_api.services.base.competitions.CompetitionsService;
import com.example.historian_api.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.example.historian_api.utils.constants.AppStrings.COMPETITIONS_IMAGES_PATH;
import static com.example.historian_api.utils.constants.AppStrings.STUDENTS_IMAGES_PATH;

@Service
@RequiredArgsConstructor
public class CompetitionsServiceImpl implements CompetitionsService {

    private final CompetitionsImagesService competitionsImagesService;
    private final CompetitionsRepository competitionsRepository;
    private final TeachersRepository teachersRepository;
    private final ImageUtils imageUtils;

    @Override
    public List<CompetitionResponseDto> getAll() {
        return competitionsRepository.findAll()
                .stream()
                .map(competition -> new CompetitionResponseDto(
                        competition.getId(),
                        competition.getTitle(),
                        competition.getDescription(),
                        competition.getPhotoUrl(),
                        competition.getTeacher().getId(),
                        competition.getTeacher().getName(),
                        competition.getTeacher().getPhotoUrl()
                ))
                .toList();
    }

    @Override
    public List<CompetitionResponseDto> getTop(Integer count) {
        return competitionsRepository.findTop(count)
                .stream()
                .map(competition -> new CompetitionResponseDto(
                        competition.getId(),
                        competition.getTitle(),
                        competition.getDescription(),
                        competition.getPhotoUrl(),
                        null,
                        null,
                        null
                ))
                .toList();
    }

    @Override
    public CompetitionResponseDto addCompetition(Integer teacherId, CompetitionRequestDto requestDto) throws IOException {

        var teacher = teachersRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Teacher with that id !!"));

        Competition competition = generateCompetition(requestDto, teacher);
        var savedCompetition = competitionsRepository.save(competition);

        return new CompetitionResponseDto(
                savedCompetition.getId(),
                savedCompetition.getTitle(),
                savedCompetition.getDescription(),
                savedCompetition.getPhotoUrl(),
                teacherId,
                teacher.getName(),
                teacher.getPhotoUrl()
        );
    }

    private Competition generateCompetition(CompetitionRequestDto requestDto, Teacher teacher) throws IOException {
        String photoUrl = null;
        CompetitionImage competitionImage = null;

        if (requestDto.photo() != null) {
            String imageTitle = generateUniqueImageTitle(requestDto.photo());

            competitionImage = insertCompetitionImageToDb(requestDto.photo(), imageTitle);
            photoUrl = imageUtils.generateImagePath(COMPETITIONS_IMAGES_PATH, imageTitle);
        }


        return new Competition(
                requestDto.title(),
                requestDto.description(),
                photoUrl,
                teacher,
                competitionImage
        );

    }

    private CompetitionImage insertCompetitionImageToDb(MultipartFile photo, String imageTitle) throws IOException {
        return competitionsImagesService.insertImage(
                new CompetitionImage(imageTitle,
                        imageUtils.compressImage(photo.getBytes()))
        );
    }

    private static String generateUniqueImageTitle(MultipartFile image) {
        return image.getName() + "-" + UUID.randomUUID();
    }
}
