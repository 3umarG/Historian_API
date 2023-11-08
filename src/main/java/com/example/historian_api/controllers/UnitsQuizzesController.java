package com.example.historian_api.controllers;

import com.example.historian_api.dtos.requests.AddQuizScoreRequestDto;
import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.courses.quizzes.UnitsQuizzesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.version}/units-quizzes")
@Tag(name = "Units Quizzes")
@RequiredArgsConstructor
public class UnitsQuizzesController {

    private final ResponseFactory200 successFactory;
    private final UnitsQuizzesService unitsQuizzesService;

    @GetMapping("/{unitId}/questions")
    public ResponseEntity<?> getAllQuestionsByUnitId(
            @PathVariable Integer unitId,
            @RequestHeader("uid") Integer studentId) {
        var response = successFactory.createResponse(unitsQuizzesService.getUnitQuizQuestions(unitId, studentId));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{unitId}/students")
    public ResponseEntity<?> addUnitQuizScoreForStudent(
            @RequestHeader("uid") Integer studentId,
            @PathVariable Integer unitId,
            @RequestBody AddQuizScoreRequestDto dto) {
        var response = successFactory.createResponse(unitsQuizzesService.solveUnitQuiz(studentId, unitId, dto));
        return ResponseEntity.ok(response);
    }
}
