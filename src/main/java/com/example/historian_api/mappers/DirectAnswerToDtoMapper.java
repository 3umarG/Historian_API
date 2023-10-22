package com.example.historian_api.mappers;

import com.example.historian_api.dtos.responses.DirectAnswerResponseDto;
import com.example.historian_api.entities.direct_questions.DirectAnswer;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DirectAnswerToDtoMapper implements Function<DirectAnswer, DirectAnswerResponseDto> {
    @Override
    public DirectAnswerResponseDto apply(DirectAnswer directAnswer) {
        return new DirectAnswerResponseDto(
                directAnswer.getId(),
                directAnswer.getContent(),
                directAnswer.getRepliedOn(),
                directAnswer.getQuestion().getId(),
                directAnswer.getTeacher().getId(),
                directAnswer.getTeacher().getName(),
                directAnswer.getTeacher().getPhotoUrl()

        );
    }
}
