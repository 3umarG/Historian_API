package com.example.historian_api.services.impl.auth;

import com.example.historian_api.dtos.requests.LoginStudentRequestDto;
import com.example.historian_api.dtos.requests.LoginTeacherRequestDto;
import com.example.historian_api.dtos.requests.RegisterStudentRequestDto;
import com.example.historian_api.dtos.requests.RegisterTeacherRequestDto;
import com.example.historian_api.dtos.responses.LoginStudentResponseDto;
import com.example.historian_api.dtos.responses.LoginTeacherResponseDto;
import com.example.historian_api.dtos.responses.RegisterStudentResponseDto;
import com.example.historian_api.dtos.responses.RegisterTeacherResponseDto;
import com.example.historian_api.entities.users.Student;
import com.example.historian_api.entities.users.StudentImage;
import com.example.historian_api.entities.users.Teacher;
import com.example.historian_api.exceptions.MismatchPasswordException;
import com.example.historian_api.exceptions.NotFoundAuthenticatedUserException;
import com.example.historian_api.exceptions.auth.AlreadyLoginPhoneWithAnotherDeviceException;
import com.example.historian_api.exceptions.auth.NotFoundPhoneNumberLoginException;
import com.example.historian_api.exceptions.auth.UsedPhoneRegisterException;
import com.example.historian_api.mappers.RegisteredStudentResponseDtoMapper;
import com.example.historian_api.repositories.users.StudentsRepository;
import com.example.historian_api.repositories.users.TeachersRepository;
import com.example.historian_api.services.base.JwtService;
import com.example.historian_api.services.base.auth.AuthService;
import com.example.historian_api.services.base.auth.StudentsImageService;
import com.example.historian_api.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.example.historian_api.utils.constants.AppStrings.*;
import static com.example.historian_api.utils.constants.ExceptionMessages.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final StudentsRepository studentsRepository;
    private final ImageUtils imageUtils;
    private final JwtService jwtService;
    private final StudentsImageService studentsImageService;
    private final RegisteredStudentResponseDtoMapper registeredStudentResponseDtoMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final TeachersRepository teachersRepository;


    @Autowired
    @Qualifier("bCryptPasswordEncoder")
    private PasswordEncoder passwordEncoder;


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
            photoUrl = imageUtils.generateImagePath(STUDENTS_IMAGES_PATH, imageTitle);
        }


        return Student.generateStudentFromRequestDto(request, photoUrl, userImage);
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

    @Override
    public LoginStudentResponseDto loginStudent(LoginStudentRequestDto loginDto) {
        Student user = getUserWithPhoneNumber(loginDto.phone());
        checkForLoginWithPhoneInDifferentDevice(loginDto.deviceSerial(), user.getDeviceSerial());
        var jwt = generateJwtTokenFromUser(user);
        authenticateStudent(loginDto, user);
        return generateLoginResponseDtoWithJwtToken(user, jwt);
    }

    @Override
    public RegisterTeacherResponseDto registerTeacher(RegisterTeacherRequestDto teacherRequestDto) {

        if(isExistedTeacherWithPhone(teacherRequestDto.phone())){
            throw new UsedPhoneRegisterException("There is already Instructor signed with that phone.");
        }

        var teacher = generateTeacherFromDto(teacherRequestDto);

        var savedTeacher = saveTeacherToDb(teacher);

        return generateRegisterTeacherResponseDto(savedTeacher);

    }

    @Override
    public LoginTeacherResponseDto loginTeacher(LoginTeacherRequestDto loginDto) {
        var teacher = findTeacherByPhone(loginDto);

        if(!isPasswordsMatched(loginDto.password(), teacher.getPassword())){
            throw new MismatchPasswordException("Your password is not correct!!");
        }

        var jwt = generateJwtTokenForTeacher(teacher);

        authenticateTeacher(teacher.getPhone(), loginDto.password());

        return generateLoginTeacherResponseDto(teacher, jwt);

    }

    private LoginTeacherResponseDto generateLoginTeacherResponseDto(Teacher teacher, String jwt) {
        return new LoginTeacherResponseDto(
                teacher.getId(),
                teacher.getName(),
                teacher.getPhone(),
                teacher.getRole().name(),
                jwt,
                true
        );
    }

    private void authenticateTeacher(String phone, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            phone,
                            password

                    )
            );
        } catch (AuthenticationException exception) {
            LOGGER.warn(exception.getMessage());
            throw new NotFoundAuthenticatedUserException(NOT_AUTHENTICATED_USER_EXCEPTION_MSG);
        }

    }

    private String generateJwtTokenForTeacher(Teacher teacher) {
        Map<String, Object> claims = teacher.getClaims();
        var jwt = jwtService.generateToken(claims, teacher);
        return jwt;

    }

    private boolean isPasswordsMatched(String requestPassword, String realPassword) {
        return passwordEncoder.matches(requestPassword, realPassword);
    }

    private Teacher findTeacherByPhone(LoginTeacherRequestDto loginDto) {
        return teachersRepository.findByPhone(loginDto.phone())
                .orElseThrow(() -> new NotFoundPhoneNumberLoginException("There is no Instructor logged in with this phone number !!"));

    }

    private RegisterTeacherResponseDto generateRegisterTeacherResponseDto(Teacher teacher) {
        return new RegisterTeacherResponseDto(
                teacher.getId(),
                teacher.getName(),
                teacher.getPhone(),
                teacher.getRole().name()
        );
    }

    private Teacher saveTeacherToDb(Teacher teacher) {
        return teachersRepository.save(teacher);
    }

    private Teacher generateTeacherFromDto(RegisterTeacherRequestDto teacherRequestDto) {
        return new Teacher(
                teacherRequestDto.name(),
                teacherRequestDto.phone(),
                passwordEncoder.encode(teacherRequestDto.password())
        );

    }

    private boolean isExistedTeacherWithPhone(String phone) {
        var instructor = teachersRepository.findByPhone(phone);
        return instructor.isPresent();
    }

    private Student getUserWithPhoneNumber(String phone) {
        return studentsRepository
                .findByPhone(phone)
                .orElseThrow(() ->
                        new NotFoundPhoneNumberLoginException(NOT_FOUND_PHONE_EXCEPTION_MSG));
    }

    private static void checkForLoginWithPhoneInDifferentDevice(String givenDeviceSerial, String loggedInDeviceSerial) {
        if (!loggedInDeviceSerial.equals(givenDeviceSerial))
            throw new AlreadyLoginPhoneWithAnotherDeviceException(USED_PHONE_IN_ANOTHER_DEVICE_EXCEPTION_MSG);
    }

    private String generateJwtTokenFromUser(Student student) {
        Map<String, Object> claims = student.getClaims();
        var jwt = jwtService.generateToken(claims, student);
        return jwt;
    }

    private void authenticateStudent(LoginStudentRequestDto request, Student user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.phone(),
                            user.getPassword()

                    )
            );
        } catch (AuthenticationException exception) {
            LOGGER.warn(exception.getMessage());
            throw new NotFoundAuthenticatedUserException(NOT_AUTHENTICATED_USER_EXCEPTION_MSG);
        }
    }

    private static LoginStudentResponseDto generateLoginResponseDtoWithJwtToken(Student student, String jwtToken) {
        return new LoginStudentResponseDto(
                student.getId(),
                student.getName(),
                student.getDeviceSerial(),
                student.getPhone(),
                student.getRole(),
                student.getGender(),
                student.isHaveSimCard(),
                student.getToken(),
                student.getPhotoUrl(),
                true,
                jwtToken
        );
    }
}
