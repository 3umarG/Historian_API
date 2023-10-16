package com.example.historian_api.controllers;

import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.courses.StudentGradesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.version}/grades")
@RequiredArgsConstructor
@Tag(name = "Grades")
public class StudentsGradesController {
    private final ResponseFactory200 successFactory;
    private final StudentGradesService gradesService;

    @GetMapping
    public ResponseEntity<?> getAllGrades() {
        var response = successFactory.createResponse(gradesService.getAllGrades());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{gradeName}")
    public ResponseEntity<?> addGrade(@PathVariable String gradeName) {
        var response = successFactory.createResponse(gradesService.addGrade(gradeName));
        return ResponseEntity.ok(response);
    }
}
