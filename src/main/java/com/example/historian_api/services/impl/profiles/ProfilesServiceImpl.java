package com.example.historian_api.services.impl.profiles;

import com.example.historian_api.dtos.responses.LoginStudentResponseDto;
import com.example.historian_api.dtos.responses.LoginTeacherResponseDto;
import com.example.historian_api.dtos.responses.TeacherProfileResponseDto;
import com.example.historian_api.entities.users.Student;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.users.StudentsRepository;
import com.example.historian_api.repositories.users.TeachersRepository;
import com.example.historian_api.services.base.feedbacks.FeedbacksService;
import com.example.historian_api.services.base.profiles.ProfilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfilesServiceImpl implements ProfilesService {

    private final StudentsRepository studentsRepository;
    private final TeachersRepository teachersRepository;
    private final FeedbacksService feedbacksService;

    @Override
    public LoginStudentResponseDto getStudentProfile(Integer studentId) {
        var student = studentsRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Student with that id !!"));

        return generateStudentResponseDto(student);
    }

    private static LoginStudentResponseDto generateStudentResponseDto(Student student) {
        return new LoginStudentResponseDto(
                student.getId(),
                student.getName(),
                student.getDeviceSerial(),
                student.getPhone(),
                student.getRole(),
                student.getToken(),
                student.getPhotoUrl(),
                true,
                student.getStudentGrade().getId(),
                student.getStudentGrade().getName(),
                student.getToken()
        );
    }

    @Override
    public TeacherProfileResponseDto getTeacherProfile(Integer teacherId) {
        var teacher = teachersRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Teacher with that id !!"));

        var teacherDto = new LoginTeacherResponseDto(
                teacher.getId(),
                teacher.getName(),
                teacher.getPhone(),
                teacher.getRole().name(),
                null,
                true,
                teacher.getAddress(),
                teacher.getSummery(),
                teacher.getFacebookUrl(),
                teacher.getWhatsAppUrl(),
                teacher.getPhotoUrl()
        );

        var top3Feedbacks = feedbacksService.getTop(3);

        return new TeacherProfileResponseDto(
                teacherDto,
                top3Feedbacks
        );

    }
}
