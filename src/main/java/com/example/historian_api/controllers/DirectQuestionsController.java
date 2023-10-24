package com.example.historian_api.controllers;

import com.example.historian_api.dtos.requests.ContentRequestDto;
import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.direct_questions.DirectQuestionsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.version}/ask-me")
@RequiredArgsConstructor
@Tag(name = "Ask Me")
public class DirectQuestionsController {

    private final DirectQuestionsService service;
    private final ResponseFactory200 successFactory;


    @PostMapping("/students/questions")
    public ResponseEntity<?> sendQuestionByStudentId(@RequestBody ContentRequestDto dto, @RequestHeader(name = "uid") Integer studentId) {
        var response = successFactory.createResponse(service.sendQuestionByStudentId(dto.content(), studentId));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/teachers/answers")
    public ResponseEntity<?> answerQuestion(@RequestBody ContentRequestDto dto, @RequestHeader(name = "qid") Integer questionId, @RequestHeader(name = "uid") Integer teacherId) {
        var response = successFactory.createResponse(service.answerQuestionByTeacher(questionId, dto.content(),teacherId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/teachers/questions")
    public ResponseEntity<?> getAllQuestionsForTeacher(@RequestParam Boolean isSolved) {
        var response = successFactory.createResponse(
                isSolved
                        ? service.getAllSolvedQuestions()
                        : service.getAllUnsolvedQuestions());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/students/questions")
    public ResponseEntity<?> getAllQuestionsForStudent(@RequestHeader(name = "uid") Integer studentId) {
        var response = successFactory.createResponse(service.getAllQuestionsWithTheirAnswersByStudentId(studentId));
        return ResponseEntity.ok(response);
    }
}
