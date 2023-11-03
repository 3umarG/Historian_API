package com.example.historian_api.dtos.requests;

import com.example.historian_api.enums.ComplaintStatus;

public record UpdatedComplaintStatusRequestDto (
        Long complaintId,
        ComplaintStatus newStatus
){
}
