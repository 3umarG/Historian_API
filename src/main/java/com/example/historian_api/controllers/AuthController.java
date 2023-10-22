package com.example.historian_api.controllers;

import com.example.historian_api.dtos.requests.LoginStudentRequestDto;
import com.example.historian_api.dtos.requests.LoginTeacherRequestDto;
import com.example.historian_api.dtos.requests.RegisterStudentRequestDto;
import com.example.historian_api.dtos.requests.RegisterTeacherRequestDto;
import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.auth.AuthService;
import com.example.historian_api.services.base.auth.StudentsImageService;
import com.example.historian_api.services.base.auth.TeacherImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final StudentsImageService studentsImageService;
    private final TeacherImageService teacherImageService;

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
    ) {
        var response = factory.createResponse(authService.loginStudent(loginDto));
        return ResponseEntity.ok().body(response);
    }


    @PostMapping(
            value = "/teachers/register",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<?> registerTeacher(
            @Valid @ModelAttribute RegisterTeacherRequestDto teacherRequestDto
    ) throws IOException {
        var response = factory.createResponse(authService.registerTeacher(teacherRequestDto));
        return ResponseEntity.ok().body(response);
    }


    @PostMapping("/teachers/login")
    public ResponseEntity<?> loginTeacher(
            @Valid @RequestBody LoginTeacherRequestDto loginDto
    ) {
        var response = factory.createResponse(authService.loginTeacher(loginDto));
        return ResponseEntity.ok().body(response);
    }

    //    /students/images/TITLE
    @GetMapping("/students/images/{title}")
    public ResponseEntity<?> getStudentImageByTitle(@PathVariable String title) {
        byte[] imageData = studentsImageService.downloadImage(title);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @GetMapping("/teachers/images/{title}")
    public ResponseEntity<?> getTeacherImageByTitle(@PathVariable String title) {
        byte[] imageData = teacherImageService.downloadImage(title);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}
