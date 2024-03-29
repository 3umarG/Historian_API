package com.example.historian_api.controllers;


import com.example.historian_api.dtos.requests.AddReplyForPostCommentByStudentRequestDto;
import com.example.historian_api.dtos.requests.AddReplyForPostCommentByTeacherRequestDto;
import com.example.historian_api.dtos.requests.ContentRequestDto;
import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.posts.CommentsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.version}/comments")
@Tag(name = "Comments")
@RequiredArgsConstructor
@Validated
public class CommentsController {

    private final ResponseFactory200 successFactory;
    private final CommentsService commentsService;


    @GetMapping("/{postId}")
    public ResponseEntity<?> getAllCommentsByPostId(@PathVariable Integer postId) {
        var response = successFactory.createResponse(commentsService.getAllCommentsByPostId(postId));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<?> addCommentToPost(@RequestHeader(name = "studentId") Integer studentId,
                                              @PathVariable Integer postId,
                                              @RequestBody ContentRequestDto dto) {
        var response = successFactory.createResponse(commentsService.addCommentByPostId(postId, studentId, dto.content()));
        return ResponseEntity.ok(response);
    }

    @PutMapping("{commentId}")
    public ResponseEntity<?> updateCommentById(
            @PathVariable Integer commentId,
            @RequestBody ContentRequestDto dto
    ) {
        var response = successFactory.createResponse(commentsService.updateCommentContentById(commentId, dto.content()));
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteCommentById(
            @PathVariable Integer commentId
    ) {
        var response = successFactory.createResponse(commentsService.deleteCommentById(commentId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{commentId}/replies")
    public ResponseEntity<?> getAllRepliesForCommentId(@PathVariable Integer commentId) {
        var response = successFactory.createResponse(commentsService.getAllRepliesByCommentId(commentId));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/replies/students")
    public ResponseEntity<?> addReplyToCommentForStudent(@RequestBody AddReplyForPostCommentByStudentRequestDto dto) {
        var response = successFactory.createResponse(commentsService.addReplyToCommentForStudent(dto));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/replies/teachers")
    public ResponseEntity<?> addReplyToCommentForTeacher(@RequestBody AddReplyForPostCommentByTeacherRequestDto dto){
        var response = successFactory.createResponse(commentsService.addReplyToCommentForTeacher(dto));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/replies/{replyId}")
    public ResponseEntity<?> deleteReplyById(@PathVariable Integer replyId){
        var response = successFactory.createResponse(commentsService.deleteReplyById(replyId));
        return ResponseEntity.ok(response);
    }
}
