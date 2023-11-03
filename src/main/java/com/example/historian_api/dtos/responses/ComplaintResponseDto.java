package com.example.historian_api.dtos.responses;

import com.example.historian_api.enums.ComplaintStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ComplaintResponseDto(
        Long id,
        Integer studentId,
        String studentName,
        String complaintContent,
        ComplaintStatus status ,
        LocalDateTime creationDate

) {
}
