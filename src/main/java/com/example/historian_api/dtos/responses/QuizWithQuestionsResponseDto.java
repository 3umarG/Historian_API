package com.example.historian_api.dtos.responses;

import com.example.historian_api.mappers.QuizQuestionWrapper;

import java.util.List;

public record QuizWithQuestionsResponseDto(
        Boolean isFinalBank,
        List<QuizQuestionWrapper> questions
) {

}
