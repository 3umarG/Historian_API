package com.example.historian_api.controllers;

import com.example.historian_api.dtos.requests.AddQuizScoreRequestDto;
import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.courses.quizzes.LessonsQuizzesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.version}/lessons")
@Tag(name = "Lessons Quizzes")
@RequiredArgsConstructor
public class LessonsController {

    private final ResponseFactory200 successResponseFactory;
    private final LessonsQuizzesService lessonsQuizzesService;

    @GetMapping("/{lessonId}/questions")
    public ResponseEntity<?> getAllQuestionsByByLessonId(
            @PathVariable Integer lessonId,
            @RequestHeader("uid") Integer studentId) {
        var response = successResponseFactory.createResponse(lessonsQuizzesService.getLessonQuestions(lessonId, studentId));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> addLessonScoreForStudent(
            @RequestHeader("uid") Integer studentId,
            @RequestHeader("lid") Integer lessonId,
            @RequestBody AddQuizScoreRequestDto dto) {
        var response = successResponseFactory.createResponse(lessonsQuizzesService.solveLessonQuiz(studentId, lessonId, dto));
        return ResponseEntity.ok(response);
    }
}
