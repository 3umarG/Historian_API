package com.example.historian_api.mappers;

import com.example.historian_api.dtos.responses.RegisterStudentResponseDto;
import com.example.historian_api.entities.users.Student;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class RegisteredStudentResponseDtoMapper implements Function<Student, RegisterStudentResponseDto> {
    @Override
    public RegisterStudentResponseDto apply(Student student) {

        return new RegisterStudentResponseDto(
                student.getId(),
                student.getName(),
                student.getDeviceSerial(),
                student.getPhone(),
                student.getRole(),
                student.getToken(),
                student.getPhotoUrl()
        );
    }
}
