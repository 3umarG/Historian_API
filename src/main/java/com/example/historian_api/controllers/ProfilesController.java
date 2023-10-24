package com.example.historian_api.controllers;

import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.profiles.ProfilesService;
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
    private final ProfilesService profilesService;

    @GetMapping("/students")
    public ResponseEntity<?> getStudentProfile(@RequestHeader(name = "uid") Integer studentId) {
        var response = successFactory.createResponse(profilesService.getStudentProfile(studentId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/teachers")
    public ResponseEntity<?> getTeacherProfile(@RequestHeader(name = "uid") Integer teacherId){
        var response = successFactory.createResponse(profilesService.getTeacherProfile(teacherId));
        return ResponseEntity.ok(response);
    }
}
