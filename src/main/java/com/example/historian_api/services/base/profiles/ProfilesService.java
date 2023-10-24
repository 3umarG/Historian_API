package com.example.historian_api.services.base.profiles;

import com.example.historian_api.dtos.responses.LoginStudentResponseDto;
import com.example.historian_api.dtos.responses.LoginTeacherResponseDto;

public interface ProfilesService {
    LoginStudentResponseDto getStudentProfile(Integer studentId);
    LoginTeacherResponseDto getTeacherProfile(Integer teacherId);
}
