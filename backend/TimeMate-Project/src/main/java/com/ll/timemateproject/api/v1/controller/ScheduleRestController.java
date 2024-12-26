package com.ll.timemateproject.api.v1.controller;

import com.ll.timemateproject.api.v1.dto.Result;
import com.ll.timemateproject.api.v1.dto.request.schedule.ScheduleCreateRequest;
import com.ll.timemateproject.api.v1.dto.request.schedule.ScheduleListRequest;
import com.ll.timemateproject.api.v1.dto.request.schedule.ScheduleModifyRequest;
import com.ll.timemateproject.api.v1.dto.response.schedule.ScheduleListResponse;
import com.ll.timemateproject.api.v1.dto.response.schedule.ScheduleModifyResponse;
import com.ll.timemateproject.domain.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleRestController {

    private final ScheduleService scheduleService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public Result<List<ScheduleListResponse>> getScheduleList(@ModelAttribute ScheduleListRequest request,
                                                              Principal principal) {
        return Result.success(scheduleService.getScheduleList(principal.getName(), request));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Result<Void> createSchedule(@RequestBody ScheduleCreateRequest request,
                                                         Principal principal) {
        scheduleService.createSchedule(principal.getName(), request);
        return Result.success(null);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Result<Void> modifySchedule(@RequestBody ScheduleModifyRequest request,
                                                         @PathVariable Long id,
                                                         Principal principal){
        scheduleService.modifySchedule(principal.getName(), id, request);
        return Result.success(null);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Result<Void> deleteSchedule(@PathVariable Long id,
                                       Principal principal){
        scheduleService.deleteSchedule(principal.getName(), id);
        return Result.success(null);
    }
}
