package com.ll.timemateproject.api.v1.dto.request.schedule;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ScheduleListRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private Long categoryId;
}
