package com.example.historian_api.services.base.auth;

import com.example.historian_api.dtos.requests.LoginStudentRequestDto;
import com.example.historian_api.dtos.requests.RegisterStudentRequestDto;
import com.example.historian_api.dtos.requests.RegisterTeacherRequestDto;
import com.example.historian_api.dtos.responses.LoginStudentResponseDto;
import com.example.historian_api.dtos.responses.RegisterStudentResponseDto;
import com.example.historian_api.dtos.responses.RegisterTeacherResponseDto;

import java.io.IOException;

public interface AuthService {
    RegisterStudentResponseDto registerStudent(RegisterStudentRequestDto requestDto) throws IOException;

    LoginStudentResponseDto loginStudent(LoginStudentRequestDto loginDto);

    RegisterTeacherResponseDto registerTeacher(RegisterTeacherRequestDto teacherRequestDto);
}
