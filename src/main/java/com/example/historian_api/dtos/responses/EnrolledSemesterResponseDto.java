package com.example.historian_api.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude
public record EnrolledSemesterResponseDto(
        Integer semesterId,
        String semesterName,
        String state,
        Integer studentId,
        String studentName,
        String studentPhotoUrl) {
}
