package com.ll.timemateproject.api.v1.dto.response.notification;

import com.ll.timemateproject.domain.notification.Notification;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.function.Function;

@Data
@Builder
public class NotificationListResponse {

    private Long id;
    private String scheduleTitle;
    private String scheduleDescription;
    private String scheduleLocation;
    private LocalDateTime scheduleStartDate;
    private LocalDateTime scheduleEndDate;
    private LocalDateTime notificationDate;
    private Boolean isRead;

    public static NotificationListResponse of(Notification notification) {
        return NotificationListResponse.builder()
                .id(notification.getId())
                .scheduleTitle(notification.getSchedule().getTitle())
                .scheduleDescription(notification.getSchedule().getDescription())
                .scheduleLocation(notification.getSchedule().getLocation())
                .scheduleStartDate(notification.getSchedule().getStartDatetime())
                .scheduleEndDate(notification.getSchedule().getEndDatetime())
                .notificationDate(notification.getNotificationTime())
                .isRead(notification.getIsRead())
                .build();
    };
}
