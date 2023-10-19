package com.example.historian_api.mappers;

import com.example.historian_api.dtos.requests.NotificationRequestDto;
import com.example.historian_api.dtos.responses.NotificationResponseDto;
import com.example.historian_api.entities.notification.Notification;
import org.springframework.stereotype.Component;

@Component

public class MapperImpl implements Mapper{
    @Override
    public NotificationResponseDto convertNotificationEntityToNotificationDto(Notification notificationEntity) {
        return NotificationResponseDto
                .builder()
                .notificationId(notificationEntity.getNotificationId())
                .notificationBody(notificationEntity.getNotificationBody())
                .notificationTitle(notificationEntity.getNotificationTitle())
                .date(notificationEntity.getDate())
                .build();
    }

    @Override
    public Notification convertNotificationDtoToNotificationEntity(NotificationRequestDto notification) {
        return Notification
                .builder()
                .notificationBody(notification.getNotificationBody())
                .notificationTitle(notification.getNotificationTitle())
                .date(notification.getDate())
                .build();
    }
}
