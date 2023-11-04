package com.example.historian_api.controllers;
import com.example.historian_api.dtos.requests.ComplaintRequestDto;
import com.example.historian_api.dtos.requests.UpdatedComplaintContentRequestDto;
import com.example.historian_api.dtos.requests.UpdatedComplaintStatusRequestDto;
import com.example.historian_api.dtos.responses.ComplaintResponseDto;
import com.example.historian_api.enums.ComplaintStatus;
import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.complaints.ComplaintsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
        List<ComplaintResponseDto> groupDates = complaintsService.getStudentComplaints(studentId);
        return ResponseEntity.ok(successFactory.createResponse(groupDates));
    }

    @GetMapping("/{studentId}/{complaintStatus}")
    public ResponseEntity<?> getComplaintByStatus(@PathVariable Integer studentId, @PathVariable ComplaintStatus complaintStatus) {
        List<ComplaintResponseDto> groupDates = complaintsService.getComplaintsByStatus(studentId, complaintStatus);
        return ResponseEntity.ok(successFactory.createResponse(groupDates));
    }

    @PostMapping
    public ResponseEntity<?> saveComplaint(@Valid @RequestBody ComplaintRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(successFactory.createResponse("Invalid request data"));
        }
        ComplaintResponseDto response = complaintsService.saveComplaint(dto);
        return ResponseEntity.ok(successFactory.createResponse(response));
    }

    @DeleteMapping("/{complaintId}")
    public ResponseEntity<?> removeComplaint(@PathVariable Long complaintId) {
        ComplaintResponseDto response = complaintsService.removeComplaint(complaintId);
        return ResponseEntity.ok(successFactory.createResponse(response));
    }

    @PutMapping("/content")
    public ResponseEntity<?> updateComplaintContent(@Valid @RequestBody UpdatedComplaintContentRequestDto dto,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(successFactory.createResponse("Invalid request data"));
        }
        ComplaintResponseDto response = complaintsService.updateContent(dto.complaintId(), dto.newContent());
        return ResponseEntity.ok(successFactory.createResponse(response));
    }

    @PutMapping("/status")
    public ResponseEntity<?> updateComplaintStatus(@Valid @RequestBody UpdatedComplaintStatusRequestDto dto,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(successFactory.createResponse("Invalid request data"));
        }
        ComplaintResponseDto response = complaintsService.updateStatus(dto.complaintId(), dto.newStatus());
        return ResponseEntity.ok(successFactory.createResponse(response));
    }

}
