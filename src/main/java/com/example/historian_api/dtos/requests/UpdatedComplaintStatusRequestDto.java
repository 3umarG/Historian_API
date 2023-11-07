package com.example.historian_api.dtos.requests;

import com.example.historian_api.enums.ComplaintStatus;
import jakarta.validation.constraints.NotNull;

public record UpdatedComplaintStatusRequestDto (
       @NotNull Long complaintId
        ,
       @NotNull ComplaintStatus newStatus
){
}
