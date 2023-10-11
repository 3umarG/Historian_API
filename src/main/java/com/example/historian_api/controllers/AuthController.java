package com.example.historian_api.controllers;

import com.example.historian_api.dtos.requests.LoginStudentRequestDto;
import com.example.historian_api.dtos.requests.LoginTeacherRequestDto;
import com.example.historian_api.dtos.requests.RegisterStudentRequestDto;
import com.example.historian_api.dtos.requests.RegisterTeacherRequestDto;
import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.auth.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("${api.version}/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
@Validated
public class AuthController {

    private final ResponseFactory200 factory;
    private final AuthService authService;

    @PostMapping(
            value = "/students/register",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<?> registerStudent(
            @Valid @ModelAttribute RegisterStudentRequestDto requestDto
            ) throws IOException {
        var response = factory.createResponse(authService.registerStudent(requestDto));
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/students/login")
    public ResponseEntity<?> loginStudent(
            @Valid @RequestBody LoginStudentRequestDto loginDto
            ){
        var response = factory.createResponse(authService.loginStudent(loginDto));
        return ResponseEntity.ok().body(response);
    }


    @PostMapping("/teachers/register")
    public ResponseEntity<?> registerTeacher(
            @Valid @RequestBody RegisterTeacherRequestDto teacherRequestDto
            ){
        return ResponseEntity.ok().build();
    }


    @PostMapping("/teachers/login")
    public ResponseEntity<?> loginTeacher(
            @Valid @RequestBody LoginTeacherRequestDto loginDto
            ){
        return ResponseEntity.ok().build();
    }
}
