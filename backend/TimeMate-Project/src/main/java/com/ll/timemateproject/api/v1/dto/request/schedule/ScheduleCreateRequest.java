package com.ll.timemateproject.api.v1.dto.request.schedule;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class ScheduleCreateRequest {
    private Long categoryId;
    private String title;
    private String description;
    private String location;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private List<LocalDateTime> notificationTimes;
}
