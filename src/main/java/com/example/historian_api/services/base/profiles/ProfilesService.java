package com.example.historian_api.services.base.profiles;

import com.example.historian_api.dtos.responses.LoginStudentResponseDto;
import com.example.historian_api.dtos.responses.LoginTeacherResponseDto;
import com.example.historian_api.dtos.responses.TeacherProfileResponseDto;

public interface ProfilesService {
    LoginStudentResponseDto getStudentProfile(Integer studentId);
    TeacherProfileResponseDto getTeacherProfile(Integer teacherId);
}
