package com.example.historian_api.dtos.requests;

import lombok.Builder;

@Builder
public record LoginStudentRequestDto(String phone, String deviceSerial) {
}
