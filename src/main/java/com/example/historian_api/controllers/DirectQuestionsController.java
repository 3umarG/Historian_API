package com.example.historian_api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.version}/ask-me")
@RequiredArgsConstructor
@Tag(name = "Ask Me")
public class DirectQuestionsController {

    @PostMapping("/students/questions/{question}")
    public ResponseEntity<?> sendQuestionByStudentId(@PathVariable String question, @RequestHeader(name = "uid") Integer studentId){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/teachers/answers/{answer}")
    public ResponseEntity<?> answerQuestion(@PathVariable String answer,@RequestHeader(name = "qid") Integer questionId){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/teachers/questions")
    public ResponseEntity<?> getAllQuestionsForTeacher(@RequestParam Boolean isSolved){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/students/questions")
    public ResponseEntity<?> getAllQuestionsForStudent(@RequestHeader(name = "uid") Integer studentId){
        return ResponseEntity.ok().build();
    }
}
