package com.example.historian_api.dtos.responses;

import com.example.historian_api.enums.Gender;
import com.example.historian_api.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public record RegisterStudentResponseDto(

        Integer id,

        String name,

        String deviceSerial,

        String phone,

        Role role,

        Gender gender,

        boolean haveSimCard,

        String token,

        String photoUrl
) {
}
