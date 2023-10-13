package com.example.historian_api.controllers;


import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.posts.BookmarksService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.version}/bookmarks")
@Tag(name = "Bookmarks")
@RequiredArgsConstructor
@Validated
public class BookmarksController {
    private final ResponseFactory200 successFactory;
    private final BookmarksService bookmarksService;

    @PostMapping("/{postId}/{studentId}")
    public ResponseEntity<?> addOrDeleteBookmark(
            @PathVariable Integer postId,
            @PathVariable Integer studentId) {
        var response = successFactory.createResponse(bookmarksService.addOrDeletePostBookMarkForStudent(postId, studentId));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getBookmarksForStudentId(@PathVariable Integer studentId) {
        var response = successFactory.createResponse(bookmarksService.getBookMarksForStudentId(studentId));
        return ResponseEntity.ok().body(response);
    }
}
