package com.ll.timemateproject.api.v1.dto.request.schedule;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ScheduleModifyRequest {
    private Long categoryId;
    private String title;
    private String description;
    private String location;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
}
