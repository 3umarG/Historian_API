package com.example.historian_api.controllers;

import com.example.historian_api.dtos.requests.ContentRequestDto;
import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.feedbacks.FeedbacksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.version}/feedbacks")
@RequiredArgsConstructor
public class FeedbacksController {

    private final FeedbacksService feedbacksService;
    private final ResponseFactory200 successFactory;
    @GetMapping()
    public ResponseEntity<?> getAll(){
        var response = successFactory.createResponse(feedbacksService.getAll());
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<?> addFeedback(@RequestHeader(name = "uid") Integer studentId, @RequestBody ContentRequestDto dto){
        var response = successFactory.createResponse(feedbacksService.addFeedback(studentId,dto.content()));
        return ResponseEntity.ok(response);
    }
}
