package com.example.historian_api.controllers;

import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.repositories.StudentClassesRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.version}/classes")
@RequiredArgsConstructor
@Tag(name = "Classes")
public class StudentsClassesController {
    private final ResponseFactory200 successFactory;

    @GetMapping
    public ResponseEntity<?> getAllClasses() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{className}")
    public ResponseEntity<?> addClass(@PathVariable String className){
        return ResponseEntity.ok().build();
    }
}
