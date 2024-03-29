package com.example.historian_api.controllers;
import com.example.historian_api.dtos.requests.ContentRequestDto;
import com.example.historian_api.dtos.requests.GradeGroupRequestDto;
import com.example.historian_api.dtos.responses.GradeGroupResponseDto;
import com.example.historian_api.entities.projections.GradeGroupProjection;
import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.dates.GradeGroupsServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.version}/groups")
@RequiredArgsConstructor
public class GradeGroupController {

    private final GradeGroupsServices gradeGroupsServices;
    private final ResponseFactory200 successFactory;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<GradeGroupResponseDto> groups = gradeGroupsServices.getAllGroups();
            return ResponseEntity.ok(successFactory.createResponse(groups));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/grades/{gradeId}")
    public ResponseEntity<?> getAllByStudentGradeId(@PathVariable Integer gradeId) {
        try {
            List<GradeGroupResponseDto> groups = gradeGroupsServices.getGroupsByGradeId(gradeId);
            return ResponseEntity.ok(successFactory.createResponse(groups));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<?> getByGroupId(@PathVariable Long groupId) {
        try {
            GradeGroupResponseDto group = gradeGroupsServices.getGroupById(groupId);
            return ResponseEntity.ok(successFactory.createResponse(group));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> saveGradeGroup(@RequestBody GradeGroupRequestDto dto) {
        try {
            GradeGroupResponseDto response = gradeGroupsServices.saveGradeGroup(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(successFactory.createResponse(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/{groupId}")
    public ResponseEntity<?> updateGroupTitle(@RequestBody ContentRequestDto dto, @PathVariable Long groupId) {
        try {
            GradeGroupResponseDto response = gradeGroupsServices.updateGroupTitle(groupId, dto.content());
            return ResponseEntity.ok(successFactory.createResponse(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
