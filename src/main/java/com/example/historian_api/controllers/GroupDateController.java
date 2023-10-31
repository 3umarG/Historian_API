package com.example.historian_api.controllers;

import com.example.historian_api.dtos.requests.GradeGroupRequestDto;
import com.example.historian_api.dtos.requests.GroupDateRequestDto;
import com.example.historian_api.dtos.responses.GroupDateResponseDto;
import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.dates.GroupDateServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.version}/dates")
@RequiredArgsConstructor
public class GroupDateController {

    private final GroupDateServices groupDateServices;
    private final ResponseFactory200 successFactory;

    @GetMapping("/{groupId}")
    public ResponseEntity<?> getGroupDate(@PathVariable Long groupId) {
        try {
            List<GroupDateResponseDto> groupDates = groupDateServices.getAllGroupDate(groupId);
            return ResponseEntity.ok(successFactory.createResponse(groupDates));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> saveGroupDate(@RequestBody GroupDateRequestDto dto) {
        try {
            GroupDateResponseDto response = groupDateServices.saveGroupDate(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(successFactory.createResponse(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
