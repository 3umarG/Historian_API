package com.example.historian_api.dtos.responses;

import com.example.historian_api.mappers.QuizWithQuestionsWrapper;

import java.util.List;

public record QuizWithQuestionsResponseDto(
        Boolean isFinalBank,
        List<QuizWithQuestionsWrapper> questions
) {

}
