package com.example.historian_api.dtos.responses;


import com.example.historian_api.enums.Gender;
import com.example.historian_api.enums.Role;

public record LoginStudentResponseDto(

        Integer id,

        String name,

        String deviceSerial,

        String phone,

        Role role,

        Gender gender,

        boolean haveSimCard,

        String token,

        String photoUrl,
        boolean isAuthenticated,
        Integer gradeId,
        String gradeName,
        String accessToken
) {


}
