package com.example.historian_api.services.base.courses.quizzes;

import com.example.historian_api.dtos.requests.AddQuizScoreRequestDto;
import com.example.historian_api.dtos.responses.QuizResultWithQuestionsResponseDto;
import com.example.historian_api.dtos.responses.QuizWithQuestionsResponseDto;

public interface UnitsQuizzesService {
    QuizWithQuestionsResponseDto getUnitQuizQuestions(Integer unitId, Integer studentId);

    QuizResultWithQuestionsResponseDto solveUnitQuiz(Integer studentId, Integer unitId, AddQuizScoreRequestDto dto);

}
