package com.ll.timemateproject.api.v1.dto.response.schedule;

import com.ll.timemateproject.domain.schedule.Schedule;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ScheduleListResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String location;

    private Long categoryId;
    private String categoryName;
    private String categoryColor;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ScheduleListResponse of(Schedule schedule) {
        return ScheduleListResponse.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .description(schedule.getDescription())
                .startDateTime(schedule.getStartDatetime())
                .endDateTime(schedule.getEndDatetime())
                .location(schedule.getLocation())
                .categoryId(schedule.getCategory().getId())
                .categoryName(schedule.getCategory().getName())
                .categoryColor(schedule.getCategory().getColor())
                .createdAt(schedule.getCreatedDate())
                .updatedAt(schedule.getModifiedDate())
                .build();
    }
}
