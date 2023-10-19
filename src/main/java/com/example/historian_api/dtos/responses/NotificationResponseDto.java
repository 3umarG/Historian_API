package com.example.historian_api.dtos.responses;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class NotificationResponseDto {
    private Long notificationId;
    private String notificationTitle;
    private String notificationBody;
    private Date date;

}
