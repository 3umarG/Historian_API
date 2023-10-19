package com.example.historian_api.mappers;

import com.example.historian_api.dtos.requests.NotificationRequestDto;
import com.example.historian_api.dtos.responses.NotificationResponseDto;
import com.example.historian_api.entities.notification.Notification;

public interface Mapper {
    NotificationResponseDto convertNotificationEntityToNotificationDto(Notification notificationEntity);
    Notification convertNotificationDtoToNotificationEntity(NotificationRequestDto notification);
}
