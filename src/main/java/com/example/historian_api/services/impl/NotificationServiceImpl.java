package com.example.historian_api.services.impl;

import com.example.historian_api.dtos.requests.NotificationRequestDto;
import com.example.historian_api.dtos.responses.NotificationResponseDto;
import com.example.historian_api.entities.notification.Notification;
import com.example.historian_api.exceptions.NotFoundNotificationException;
import com.example.historian_api.mappers.NotificationMapper;
import com.example.historian_api.mappers.NotificationMapperImpl;
import com.example.historian_api.repositories.NotificationRepository;
import com.example.historian_api.services.base.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper mapper;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationMapperImpl mapper) {
        this.notificationRepository = notificationRepository;
        this.mapper = mapper;
    }

    @Override
    public List<NotificationResponseDto> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications
                .stream()
                .map(mapper::convertNotificationEntityToNotificationDto)
                .toList();
    }

    @Override
    public NotificationResponseDto addNewNotification(NotificationRequestDto notificationRequestDto) {
        Notification newNotification = mapper.convertNotificationDtoToNotificationEntity(notificationRequestDto);
        if (newNotification != null) {
            notificationRepository.save(newNotification);
            NotificationResponseDto savedNotification = mapper.convertNotificationEntityToNotificationDto(newNotification);
            return savedNotification;
        }
        //todo:add empty notification exception later
        return null;
    }

    @Override
    public boolean removeNotification(Long id) {
        boolean isExist = this.notificationRepository.existsById(id);
        if (!isExist) {
            throw new NotFoundNotificationException("Notification Not Found");
        }
        this.notificationRepository.deleteById(id);
        return true;
    }
}
