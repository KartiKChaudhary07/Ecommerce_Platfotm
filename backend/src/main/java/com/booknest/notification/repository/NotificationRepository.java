package com.booknest.notification.repository;

import com.booknest.notification.entity.Notification;
import com.booknest.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByNotificationDateDesc(User user);
    List<Notification> findByUserAndIsReadFalse(User user);
}
