package com.booknest.notification.service;

import com.booknest.notification.entity.Notification;
import com.booknest.user.entity.User;

import java.util.List;

public interface NotificationService {
    Notification sendNotification(User user, String message);
    List<Notification> getUserNotifications(User user);
    void markAsRead(Long notificationId);
    void markAllAsRead(User user);
}
