package com.example.historian_api.controllers;

import com.example.historian_api.factories.impl.ResponseFactory200;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.version}/semesters")
@RequiredArgsConstructor
public class SemestersController {
    private final ResponseFactory200 successResponseFactory;

    @GetMapping("/grades/{gradeId}")
    public ResponseEntity<?> getSemestersForGradeId(
            @PathVariable Integer gradeId,
            @RequestHeader("uid") Integer studentId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{semesterId}/courses")
    public ResponseEntity<?> getCoursesForSemesterId(@PathVariable Integer semesterId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{semesterId}")
    public ResponseEntity<?> subscribeStudentInSemester(
            @PathVariable Integer semesterId,
            @RequestHeader("uid") Integer studentId) {
        return ResponseEntity.ok().build();
    }
}
