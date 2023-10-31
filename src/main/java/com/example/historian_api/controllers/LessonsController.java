package com.example.historian_api.controllers;

import com.example.historian_api.dtos.requests.AddQuizScoreRequestDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.version}/lessons")
@Tag(name = "Lessons Quizzes")
@RequiredArgsConstructor
public class LessonsController {

    @GetMapping("/{lessonId}/questions")
    public ResponseEntity<?> getAllQuestionsByByLessonId(
            @PathVariable Integer lessonId,
            @RequestHeader("uid") Integer studentId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> addLessonScoreForStudent(
            @RequestHeader("uid") Integer studentId,
            @RequestHeader("lid") Integer lessonId,
            @RequestBody AddQuizScoreRequestDto dto) {
        return ResponseEntity.ok().build();
    }
}
