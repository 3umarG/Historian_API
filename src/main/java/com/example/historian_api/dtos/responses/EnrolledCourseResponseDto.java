package com.example.historian_api.dtos.responses;

public record EnrolledCourseResponseDto(
        Integer courseId,
        String courseTitle,
        String state,
        Integer studentId,
        String studentName,
        String studentPhotoUrl
) {
}
