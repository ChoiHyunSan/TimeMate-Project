package com.ll.timemateproject.api.v1.controller;

import com.ll.timemateproject.api.v1.dto.Result;
import com.ll.timemateproject.api.v1.dto.request.schedule.ScheduleCreateRequest;
import com.ll.timemateproject.api.v1.dto.request.schedule.ScheduleListRequest;
import com.ll.timemateproject.api.v1.dto.request.schedule.ScheduleModifyRequest;
import com.ll.timemateproject.api.v1.dto.response.schedule.ScheduleCreateResponse;
import com.ll.timemateproject.api.v1.dto.response.schedule.ScheduleListResponse;
import com.ll.timemateproject.api.v1.dto.response.schedule.ScheduleModifyResponse;
import com.ll.timemateproject.domain.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleRestController {

    private final ScheduleService scheduleService;

    @GetMapping
    public Result<List<ScheduleListResponse>> getScheduleList(@ModelAttribute ScheduleListRequest request) {
        return Result.success(null);
    }

    @PostMapping
    public Result<ScheduleCreateResponse> createSchedule(@RequestBody ScheduleCreateRequest request){
        return Result.success(null);
    }

    @PutMapping("/{id}")
    public Result<ScheduleModifyResponse> modifySchedule(@RequestBody ScheduleModifyRequest request,
                                                         @PathVariable String id){
        return Result.success(null);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteSchedule(@PathVariable Long id){
        return Result.success(null);
    }
}
