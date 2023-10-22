package com.example.historian_api.mappers;

import com.example.historian_api.dtos.responses.DirectQuestionResponseDto;
import com.example.historian_api.entities.direct_questions.DirectQuestion;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DirectQuestionToDtoMapper implements Function<DirectQuestion, DirectQuestionResponseDto> {
    @Override
    public DirectQuestionResponseDto apply(DirectQuestion directQuestion) {
        return new DirectQuestionResponseDto(
                directQuestion.getId(),
                directQuestion.getContent(),
                directQuestion.getAskedOn(),
                directQuestion.getStudent().getId(),
                directQuestion.getStudent().getName()
        );
    }
}
