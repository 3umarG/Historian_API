package com.example.historian_api.mappers;

import com.example.historian_api.dtos.responses.ComplaintResponseDto;
import com.example.historian_api.entities.complaints.Complaint;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ComplaintToComplaintResponseDtoMapper implements Function<Complaint, ComplaintResponseDto> {
    @Override
    public ComplaintResponseDto apply(Complaint complaint) {
        return ComplaintResponseDto
                .builder()
                .id(complaint.getId())
                .studentId(complaint.getCreator().getId())
                .studentName(complaint.getCreator().getName())
                .complaintContent(complaint.getContent())
                .status(complaint.getStatus())
                .creationDate(complaint.getCreationDate())
                .build();
    }
}
