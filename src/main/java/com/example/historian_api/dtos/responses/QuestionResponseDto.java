package com.example.historian_api.dtos.responses;

import java.util.List;

public record QuestionResponseDto(
        Integer questionId,
        String question,
        List<String> answers,
        Integer correctAnswerIndex,
        Integer studentAnswerIndex,
        String correctAnswer,
        String studentAnswer,
        String correctAnswerDescription,
        String photoUrl,
        Boolean isCheckedAnswer
) {
}
