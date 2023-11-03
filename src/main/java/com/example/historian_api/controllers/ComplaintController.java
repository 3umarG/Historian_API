package com.example.historian_api.controllers;
import com.example.historian_api.dtos.requests.ComplaintRequestDto;
import com.example.historian_api.dtos.requests.GroupDateRequestDto;
import com.example.historian_api.dtos.requests.UpdatedComplaintStatusRequestDto;
import com.example.historian_api.dtos.responses.ComplaintResponseDto;
import com.example.historian_api.dtos.responses.GroupDateResponseDto;
import com.example.historian_api.enums.ComplaintStatus;
import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.complaints.ComplaintsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.version}/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ResponseFactory200 successFactory;
    private final ComplaintsService complaintsService;

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentComplaints(@PathVariable Integer studentId) {
        try {
            List<ComplaintResponseDto> groupDates = complaintsService.getStudentComplaints(studentId);
            return ResponseEntity.ok(successFactory.createResponse(groupDates));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> saveComplaint(@RequestBody ComplaintRequestDto dto) {
        try {
            ComplaintResponseDto response = complaintsService.saveComplaint(dto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(successFactory.createResponse(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    @PutMapping()
    public ResponseEntity<?> updateComplaintStatus(@RequestBody UpdatedComplaintStatusRequestDto dto) {
        try {
            ComplaintResponseDto response = complaintsService.updateStatus(dto.complaintId(),dto.newStatus());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(successFactory.createResponse(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
