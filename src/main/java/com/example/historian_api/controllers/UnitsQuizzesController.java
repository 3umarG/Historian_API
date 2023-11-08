package com.example.historian_api.controllers;

import com.example.historian_api.dtos.requests.AddQuizScoreRequestDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.version}/units-quizzes")
@Tag(name = "Units Quizzes")
@RequiredArgsConstructor
public class UnitsQuizzesController {

    @GetMapping("/{unitId}/questions")
    public ResponseEntity<?> getAllQuestionsByUnitId(
            @PathVariable Integer unitId,
            @RequestHeader("uid") Integer studentId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{unitId}/students")
    public ResponseEntity<?> addUnitQuizScoreForStudent(
            @RequestHeader("uid") Integer studentId,
            @PathVariable Integer unitId,
            @RequestBody AddQuizScoreRequestDto dto) {
        return ResponseEntity.ok().build();
    }
}
