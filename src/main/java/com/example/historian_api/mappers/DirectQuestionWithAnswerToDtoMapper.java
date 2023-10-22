package com.example.historian_api.mappers;

import com.example.historian_api.dtos.responses.DirectQuestionWithAnswerResponseDto;
import com.example.historian_api.entities.direct_questions.DirectQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class DirectQuestionWithAnswerToDtoMapper implements Function<DirectQuestion, DirectQuestionWithAnswerResponseDto> {

    private final DirectQuestionToDtoMapper questionToDtoMapper;
    private final DirectAnswerToDtoMapper answerToDtoMapper;

    @Override
    public DirectQuestionWithAnswerResponseDto apply(DirectQuestion directQuestion) {
        return new DirectQuestionWithAnswerResponseDto(
                questionToDtoMapper.apply(directQuestion),
                directQuestion.getAnswer() != null,
                directQuestion.getAnswer() != null
                        ? answerToDtoMapper.apply(directQuestion.getAnswer())
                        : null
        );
    }
}
