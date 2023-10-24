package com.example.historian_api.dtos.responses;

import java.util.List;

public record TeacherProfileResponseDto(
        LoginTeacherResponseDto teacher,
        List<FeedbackResponseDto> feedbacks,
        List<CompetitionResponseDto> competitions
) {
}
