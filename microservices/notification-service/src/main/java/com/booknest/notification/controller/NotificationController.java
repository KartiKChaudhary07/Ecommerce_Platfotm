package com.booknest.notification.controller;

import com.booknest.notification.entity.Notification;
import com.booknest.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping
    public ResponseEntity<List<Notification>> getMyNotifications(@RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(notificationRepository.findByUserIdOrderByCreatedAtDesc(userId));
    }

    @PostMapping
    public ResponseEntity<Notification> sendNotification(@RequestBody Notification notification) {
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);
        return ResponseEntity.ok(notificationRepository.save(notification));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id) {
        return notificationRepository.findById(id).map(notification -> {
            notification.setRead(true);
            return ResponseEntity.ok(notificationRepository.save(notification));
        }).orElse(ResponseEntity.notFound().build());
    }
}
