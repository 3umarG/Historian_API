package com.example.historian_api.controllers;

import com.example.historian_api.dtos.requests.ContentRequestDto;
import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.courses.SemestersService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.version}/semesters")
@RequiredArgsConstructor
@Tag(name = "Semesters")
public class SemestersController {

    private final ResponseFactory200 successResponseFactory;
    private final SemestersService semestersService;

    @GetMapping("/grades/{gradeId}")
    public ResponseEntity<?> getSemestersForGradeId(
            @PathVariable Integer gradeId,
            @RequestHeader("uid") Integer studentId) {
        var response = successResponseFactory.createResponse(semestersService.getAllSemestersInGradeForStudent(gradeId, studentId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{semesterId}/courses")
    public ResponseEntity<?> getCoursesForSemesterId(@PathVariable Integer semesterId) {
        var response = successResponseFactory.createResponse(semestersService.getCoursesInSemester(semesterId));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{semesterId}/vodafone-cash")
    public ResponseEntity<?> subscribeStudentInSemesterByVodafoneCash(
            @PathVariable Integer semesterId,
            @RequestHeader("uid") Integer studentId) {
        var response = successResponseFactory.createResponse(semestersService.subscribeStudentInSemesterByVodafoneCash(studentId,semesterId));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{semesterId}/subscription-card")
    public ResponseEntity<?> subscribeStudentInSemesterByCode(
            @PathVariable Integer semesterId,
            @RequestHeader("uid") Integer studentId,
            @RequestBody ContentRequestDto dto) {
        var response = successResponseFactory.createResponse(semestersService.subscribeStudentInSemesterByCode(studentId,semesterId, dto.content()));
        return ResponseEntity.ok(response);
    }
}
