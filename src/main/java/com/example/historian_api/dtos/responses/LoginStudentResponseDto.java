package com.example.historian_api.dtos.responses;


import com.example.historian_api.enums.Gender;
import com.example.historian_api.enums.Role;

public record LoginStudentResponseDto(

        Integer id,

        String name,

        String deviceSerial,

        String phone,

        Role role,

        String token,

        String photoUrl,
        boolean isAuthenticated,
        Integer gradeId,
        String gradeName,
        String accessToken
) {


}
