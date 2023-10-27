package com.example.historian_api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.version}/courses")
@RequiredArgsConstructor
public class CoursesController {

    @GetMapping
    public ResponseEntity<?> getGradeCoursesForStudent(
            @RequestHeader("uid") Integer studentId,
            @RequestHeader("gid") Integer gradeId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<?> getSubscribedCoursesForStudent(@RequestHeader("uid") Integer studentId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/subscriptions")
    public ResponseEntity<?> subscribeCourseByStudent(
            @RequestHeader("uid") Integer studentId,
            @RequestHeader("cid") Integer courseId
    ){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/lessons")
    public ResponseEntity<?> getCourseLessonsForStudent(
            @RequestHeader("uid") Integer studentId,
            @RequestHeader("cid") Integer courseId) {
        return ResponseEntity.ok().build();
    }
}
