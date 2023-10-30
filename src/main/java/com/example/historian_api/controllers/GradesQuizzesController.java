package com.example.historian_api.controllers;

import com.example.historian_api.dtos.requests.AddQuizScoreRequestDto;
import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.courses.quizzes.QuizzesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.version}/grades/quizzes")
@Tag(name = "Quizzes")
@RequiredArgsConstructor
public class GradesQuizzesController {

    private final QuizzesService quizzesService;
    private final ResponseFactory200 successResponseFactory;

    @GetMapping
    public ResponseEntity<?> getAllQuizzesByGradeId(
            @RequestHeader("gid") Integer gradeId,
            @RequestHeader("uid") Integer studentId) {
        var response = successResponseFactory.createResponse(quizzesService.getGradeQuizzesForStudent(gradeId, studentId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{quizId}/questions")
    public ResponseEntity<?> getAllQuestionsByQuizId(
            @PathVariable Integer quizId,
            @RequestHeader("uid") Integer studentId) {


        var response = successResponseFactory.createResponse(quizzesService.getGradeQuizQuestions(quizId, studentId));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> addQuizScoreByStudentId(
            @RequestHeader("uid") Integer studentId,
            @RequestHeader("qid") Integer quizId,
            @RequestBody AddQuizScoreRequestDto dto) {
        return ResponseEntity.ok().build();
    }
}
