package com.example.historian_api.dtos.requests;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class NotificationRequestDto {
    private String notificationTitle;
    private String notificationBody;
    private Date date;
}
