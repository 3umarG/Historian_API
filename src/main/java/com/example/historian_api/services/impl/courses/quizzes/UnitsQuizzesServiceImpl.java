package com.example.historian_api.services.impl.courses.quizzes;

import com.example.historian_api.dtos.requests.AddQuizScoreRequestDto;
import com.example.historian_api.dtos.responses.QuizResultWithQuestionsResponseDto;
import com.example.historian_api.dtos.responses.QuizWithQuestionsResponseDto;
import com.example.historian_api.services.base.courses.quizzes.UnitsQuizzesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnitsQuizzesServiceImpl implements UnitsQuizzesService {
    @Override
    public QuizWithQuestionsResponseDto getUnitQuizQuestions(Integer unitId, Integer studentId) {
        return null;
    }

    @Override
    public QuizResultWithQuestionsResponseDto solveUnitQuiz(Integer studentId, Integer unitId, AddQuizScoreRequestDto dto) {
        return null;
    }
}
