package com.example.historian_api.services.base.courses.quizzes;

import com.example.historian_api.dtos.requests.AddQuizScoreRequestDto;
import com.example.historian_api.dtos.responses.QuizResultWithQuestionsResponseDto;
import com.example.historian_api.dtos.responses.QuizWithQuestionsResponseDto;

public interface LessonsQuizzesService {
    QuizWithQuestionsResponseDto getLessonQuestions(Integer lessonId, Integer studentId);

    QuizResultWithQuestionsResponseDto solveLessonQuiz(Integer studentId, Integer lessonId, AddQuizScoreRequestDto dto);

}
