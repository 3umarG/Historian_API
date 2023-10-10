package com.example.historian_api.services.impl.auth;

import com.example.historian_api.dtos.requests.RegisterStudentRequestDto;
import com.example.historian_api.dtos.responses.RegisterStudentResponseDto;
import com.example.historian_api.entities.users.Student;
import com.example.historian_api.entities.users.StudentImage;
import com.example.historian_api.exceptions.auth.UsedPhoneRegisterException;
import com.example.historian_api.mappers.RegisteredStudentResponseDtoMapper;
import com.example.historian_api.repositories.users.StudentsRepository;
import com.example.historian_api.services.base.JwtService;
import com.example.historian_api.services.base.auth.AuthService;
import com.example.historian_api.services.base.auth.StudentsImageService;
import com.example.historian_api.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static com.example.historian_api.utils.constants.AppStrings.*;
import static com.example.historian_api.utils.constants.ExceptionMessages.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final StudentsRepository studentsRepository;
    private final ImageUtils imageUtils;
    private final JwtService jwtService;
    private final StudentsImageService studentsImageService;
    private final RegisteredStudentResponseDtoMapper registeredStudentResponseDtoMapper;


    @Override
    public RegisterStudentResponseDto registerStudent(RegisterStudentRequestDto requestDto) throws IOException {
        checkForExistedUserSigned(requestDto);

        Student user = generateStudentFromRequestDto(requestDto);

        studentsRepository.save(user);

        return registeredStudentResponseDtoMapper.apply(user);
    }

    private void checkForExistedUserSigned(RegisterStudentRequestDto request) {
        Optional<Student> existedUser = studentsRepository
                .findByPhone(request.phone());

        if (existedUser.isPresent()) {
            throw new UsedPhoneRegisterException(USED_PHONE_NUMBER_EXCEPTION_MSG);
        }
    }

    public Student generateStudentFromRequestDto(RegisterStudentRequestDto request) throws IOException {
        String photoUrl = null;
        StudentImage userImage = null;

        if (request.photo() != null) {
            String imageTitle = generateUniqueImageTitle(request.photo());

            userImage = insertImageToDbWithTitle(request, imageTitle);
            photoUrl = imageUtils.generateImagePath(STUDENTS_IMAGES_PATH,imageTitle);
        }


        return Student.generateStudentFromRequestDto(request,photoUrl,userImage);
    }

    private static String generateUniqueImageTitle(MultipartFile image) {
        return image.getName() + "-" + UUID.randomUUID();
    }

    private StudentImage insertImageToDbWithTitle(RegisterStudentRequestDto request, String imageTitle) throws IOException {
        return studentsImageService.insertImage(new StudentImage(
                imageTitle,
                imageUtils.compressImage(request.photo().getBytes())
        ));
    }

}
