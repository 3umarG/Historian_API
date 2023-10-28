package com.example.historian_api.dtos.responses;

public record UnitResponseDto(
        Integer id,
        String title,
        String content,
        Integer numOfVideos,
        Integer numOfQuizzes

) {
}
