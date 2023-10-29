package com.example.historian_api.controllers;

import com.example.historian_api.dtos.requests.AddQuizScoreRequestDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.version}/grades/quizzes")
@Tag(name = "Quizzes")
public class GradesQuizzesController {

    @GetMapping
    public ResponseEntity<?> getAllQuizzesByGradeId(
            @RequestHeader("gid") Integer gradeId,
            @RequestHeader("uid") Integer studentId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{quizId}/questions")
    public ResponseEntity<?> getAllQuestionsByQuizId(
            @RequestParam("isSolved") Boolean isQuizSolved,
            @PathVariable String quizId,
            @RequestHeader("uid") Integer studentId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> addQuizScoreByStudentId(
            @RequestHeader("uid") Integer studentId,
            @RequestHeader("qid") Integer quizId,
            @RequestBody AddQuizScoreRequestDto dto) {
        return ResponseEntity.ok().build();
    }
}
