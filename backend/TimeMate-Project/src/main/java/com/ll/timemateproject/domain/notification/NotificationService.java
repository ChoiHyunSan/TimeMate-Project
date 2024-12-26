package com.ll.timemateproject.domain.notification;

import com.ll.timemateproject.api.v1.dto.response.notification.NotificationListResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationQueryRepository notificationQueryRepository;

    @Transactional
    public List<NotificationListResponse> getNotificationList(String username) {
        return notificationQueryRepository.findByUsername(username)
                .stream()
                .map(NotificationListResponse::of)
                .toList();
    }

    @Transactional
    public void markAsRead(String name, Long id) {
        Notification notification = notificationQueryRepository.findByIdAndUsername(id, name);
        notification.read();
    }
}
