package com.example.historian_api.controllers;

import com.example.historian_api.factories.impl.ResponseFactory200;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.version}/profiles")
@RequiredArgsConstructor
public class ProfilesController {

    private final ResponseFactory200 successFactory;

    @GetMapping("/students")
    public ResponseEntity<?> getStudentProfile(@RequestHeader(name = "uid") Integer studentId) {
//        var response = successFactory.createResponse();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/teachers")
    public ResponseEntity<?> getTeacherProfile(@RequestHeader(name = "uid") Integer teacherId){
        return ResponseEntity.ok().build();
    }
}
