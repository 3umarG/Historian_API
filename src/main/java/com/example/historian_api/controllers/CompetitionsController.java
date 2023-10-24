package com.example.historian_api.controllers;

import com.example.historian_api.dtos.requests.CompetitionRequestDto;
import com.example.historian_api.entities.competitions.CompetitionImage;
import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.competitions.CompetitionsService;
import com.example.historian_api.services.base.images.ImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("${api.version}/competitions")
@RequiredArgsConstructor
public class CompetitionsController {

    private final ResponseFactory200 successFactory;
    private final CompetitionsService competitionsService;


    @Autowired
    @Qualifier("CompetitionsImageService")
    private ImagesService<CompetitionImage> competitionsImagesService;

    @PostMapping(
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<?> addCompetition(@RequestHeader("uid") Integer teacherId, @ModelAttribute CompetitionRequestDto dto) throws IOException {
        var response = successFactory.createResponse(competitionsService.addCompetition(teacherId, dto));
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<?> getAllCompetitions() {
        var response = successFactory.createResponse(competitionsService.getAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/images/{title}")
    public ResponseEntity<?> getCompetitionImageByTitle(@PathVariable String title) {
        byte[] imageData = competitionsImagesService.downloadImage(title);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}
