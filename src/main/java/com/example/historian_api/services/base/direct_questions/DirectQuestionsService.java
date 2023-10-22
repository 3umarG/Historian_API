package com.example.historian_api.services.base.direct_questions;

import com.example.historian_api.dtos.responses.DirectAnswerResponseDto;
import com.example.historian_api.dtos.responses.DirectQuestionResponseDto;
import com.example.historian_api.dtos.responses.DirectQuestionWithAnswerResponseDto;

import java.util.List;

public interface DirectQuestionsService {
    DirectQuestionResponseDto sendQuestionByStudentId(String questionContent,Integer studentId);
    DirectAnswerResponseDto answerQuestionByTeacher(Integer questionId,String answerContent);
    List<DirectQuestionResponseDto> getAllUnsolvedQuestions();
    List<DirectQuestionWithAnswerResponseDto> getAllSolvedQuestions();
    List<DirectQuestionWithAnswerResponseDto> getAllQuestionsWithTheirAnswersByStudentId(Integer studentId);
}
