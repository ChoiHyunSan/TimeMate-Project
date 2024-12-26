package com.ll.timemateproject.api.v1.dto.request.notification;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationCreateRequest {
    private LocalDateTime notificationTime;
}
