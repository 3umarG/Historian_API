package com.example.historian_api.controllers;

import com.example.historian_api.dtos.requests.PostRequestDto;
import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.posts.PostsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${api.version}/posts")
@Tag(name = "Posts")
@RequiredArgsConstructor
@Validated
public class PostsController {

    private final ResponseFactory200 successFactory;
    private final PostsService postsService;


    @GetMapping()
    public ResponseEntity<?> getAllPosts(@RequestParam(required = false) Integer studentId) {
        var response = successFactory.createResponse(postsService.getAll(studentId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/images/{title}")
    public ResponseEntity<?> getPostImage(@PathVariable String title) {
        byte[] imageData = postsService.downloadPostImage(title);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PostMapping(
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<?> addPost(@ModelAttribute PostRequestDto dto) {

        var response = successFactory.createResponse(postsService.addPost(dto
        ));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getById(@PathVariable Integer postId) {
        var response = successFactory.createResponse(postsService.getPostById(postId));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deleteById(@PathVariable Integer postId) {
        var response = successFactory.createResponse(postsService.deletePostById(postId));
        return ResponseEntity.ok(response);
    }

    @PutMapping(
            path = "/{postId}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<?> updateById(@ModelAttribute PostRequestDto dto,
                                        @PathVariable Integer postId) {

        var response = successFactory.createResponse(postsService.updatePostById(postId, dto));
        return ResponseEntity.ok(response);
    }

}
