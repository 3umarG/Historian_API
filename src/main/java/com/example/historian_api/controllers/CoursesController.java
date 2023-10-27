package com.example.historian_api.controllers;

import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.courses.CoursesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.version}/courses")
@RequiredArgsConstructor
public class CoursesController {

    private final CoursesService coursesService;
    private final ResponseFactory200 successResponseFactory;

    @GetMapping
    public ResponseEntity<?> getGradeCoursesForStudent(
            @RequestHeader("uid") Integer studentId,
            @RequestHeader("gid") Integer gradeId) {
        var response = successResponseFactory.createResponse(coursesService.getAllCoursesByGradeIdForStudent(gradeId, studentId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<?> getSubscribedCoursesForStudent(@RequestHeader("uid") Integer studentId) {
        var response = successResponseFactory.createResponse(coursesService.getAllSubscribedCoursesForStudent(studentId));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/subscriptions")
    public ResponseEntity<?> subscribeCourseByStudent(
            @RequestHeader("uid") Integer studentId,
            @RequestHeader("cid") Integer courseId
    ) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/units/lessons")
    public ResponseEntity<?> getUnitLessonsForStudent(
            @RequestHeader("uid") Integer studentId,
            @RequestHeader("unitId") Integer unitId) {
        var response = successResponseFactory.createResponse(coursesService.getUnitLessonsForStudent(unitId,studentId));
        return ResponseEntity.ok(response);
    }
}
