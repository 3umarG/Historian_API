package com.example.historian_api.controllers;


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
    public ResponseEntity<?> getAllCommentsByPostId(@PathVariable Integer postId){
        var response = commentsService.getAllCommentsByPostId(postId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<?> addCommentToPost(@RequestParam("studentId") Integer studentId,
                                              @PathVariable Integer postId,
                                              @RequestParam("content") String content){
        var response = successFactory.createResponse(commentsService.addCommentByPostId(postId,studentId,content));
        return ResponseEntity.ok(response);
    }

    @PutMapping("{commentId}")
    public ResponseEntity<?> updateCommentById(
            @PathVariable Integer commentId,
            @RequestParam("content") String contentUpdated
    ){
        var response = successFactory.createResponse(commentsService.updateCommentContentById(commentId,contentUpdated));
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteCommentById(
            @PathVariable Integer commentId
    ){
        var response = successFactory.createResponse(commentsService.deleteCommentById(commentId));
        return ResponseEntity.ok(response);
    }

    // TODO : will add replies endpoints here
}
