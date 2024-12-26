package com.ll.timemateproject.api.v1.dto.request.schedule;
import lombok.Builder;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Builder
@Data
public class ScheduleListRequest {
    @NotEmpty
    private LocalDate startDate;
    @NotEmpty
    private LocalDate endDate;
    private Long categoryId;
}
