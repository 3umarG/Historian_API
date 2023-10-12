package com.example.historian_api.controllers;

import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.posts.LikesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// TODO : will change this controller later
@RestController
@RequestMapping("${api.version}/likes")
@Tag(name = "Likes")
@RequiredArgsConstructor
@Validated
public class LikesController {
    private final ResponseFactory200 successFactory;
    private final LikesService likesService;


    @GetMapping("/{postId}")
    public ResponseEntity<?> getAllLikes(@PathVariable @NotNull Integer postId) {
        return ResponseEntity.ok(successFactory.createResponse(likesService.getAllLikesByPostId(postId)));
    }


    @PostMapping("/{postId}")
    public ResponseEntity<?> addLikeOrDelete(@PathVariable @NotNull Integer postId,
                                             @RequestParam Integer studentId) {
        var response = successFactory.createResponse(likesService.addOrRemoveLikeByPostId(postId, studentId));
        return ResponseEntity.ok(response);
    }
}
