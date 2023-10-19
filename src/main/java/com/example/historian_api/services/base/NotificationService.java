package com.example.historian_api.services.base;


import com.example.historian_api.dtos.requests.NotificationRequestDto;
import com.example.historian_api.dtos.responses.NotificationResponseDto;

import java.util.List;

public interface NotificationService {
    List<NotificationResponseDto> getAllNotifications();

    NotificationResponseDto addNewNotification(NotificationRequestDto notificationRequestDto);
    boolean removeNotification(Long id);


}
