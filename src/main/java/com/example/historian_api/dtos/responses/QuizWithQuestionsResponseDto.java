package com.example.historian_api.dtos.responses;

import java.util.List;

public record QuizWithQuestionsResponseDto(
        Boolean isFinalBank,
        List<QuestionResponseDto> questions
) {

}
