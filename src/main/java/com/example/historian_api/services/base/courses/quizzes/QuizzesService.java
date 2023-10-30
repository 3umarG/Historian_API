package com.example.historian_api.services.base.courses.quizzes;

import com.example.historian_api.dtos.responses.GradeQuizResponseDto;
import com.example.historian_api.dtos.responses.QuizWithQuestionsResponseDto;

import java.util.List;

public interface QuizzesService {
    List<GradeQuizResponseDto> getGradeQuizzesForStudent(Integer gradeId , Integer studentId);

    QuizWithQuestionsResponseDto getGradeQuizQuestions(Integer quizId, Integer studentId);
}
