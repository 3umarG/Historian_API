package com.example.historian_api.services.base.complaints;

import com.example.historian_api.dtos.requests.ComplaintRequestDto;
import com.example.historian_api.dtos.responses.ComplaintResponseDto;
import com.example.historian_api.enums.ComplaintStatus;

import java.util.List;

public interface ComplaintsService {
    ComplaintResponseDto saveComplaint(ComplaintRequestDto complaintRequestDto);
    ComplaintResponseDto updateStatus(Long complaintId, ComplaintStatus newStatus);
    ComplaintResponseDto updateContent(Long complaintId, String newContent);
    ComplaintResponseDto findComplaintById(Long complaintId);
    ComplaintResponseDto removeComplaint(Long complaintId);
    List<ComplaintResponseDto>getStudentComplaints(Integer studentId);
    List<ComplaintResponseDto>getComplaintsByStatus(Integer studentId,ComplaintStatus status);
}
