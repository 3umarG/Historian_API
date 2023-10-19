package com.example.historian_api.controllers;
import com.example.historian_api.dtos.requests.NotificationRequestDto;
import com.example.historian_api.dtos.responses.NotificationResponseDto;
import com.example.historian_api.factories.impl.ResponseFactory200;
import com.example.historian_api.services.base.notification.NotificationService;
import com.example.historian_api.services.impl.NotificationServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final ResponseFactory200 successFactory;

    @Autowired
    public NotificationController(NotificationServiceImpl notificationService, ResponseFactory200 successFactory) {
        this.notificationService = notificationService;
        this.successFactory = successFactory;
    }

    @GetMapping()
    public ResponseEntity<?> getAllCourse() {
        return ResponseEntity.ok(successFactory.createResponse(notificationService.getAllNotifications()));
    }

    @PostMapping()
    public ResponseEntity<?> saveNotification(@RequestBody NotificationRequestDto notification) {
        NotificationResponseDto savedNotification = notificationService.addNewNotification(notification);
        return ResponseEntity.ok(successFactory.createResponse(savedNotification));
    }

    @DeleteMapping()
    public ResponseEntity<?> removeNotification(@RequestParam Long id) {
        return ResponseEntity.ok(successFactory.createResponse(notificationService.removeNotification(id)));
    }

}
