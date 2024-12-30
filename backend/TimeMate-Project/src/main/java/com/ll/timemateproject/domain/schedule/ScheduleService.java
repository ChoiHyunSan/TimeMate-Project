package com.ll.timemateproject.domain.schedule;

import com.ll.timemateproject.api.v1.dto.request.schedule.ScheduleCreateRequest;
import com.ll.timemateproject.api.v1.dto.request.schedule.ScheduleListRequest;
import com.ll.timemateproject.api.v1.dto.request.schedule.ScheduleModifyRequest;
import com.ll.timemateproject.api.v1.dto.response.schedule.ScheduleListResponse;
import com.ll.timemateproject.domain.category.Category;
import com.ll.timemateproject.domain.category.CategoryRepository;
import com.ll.timemateproject.domain.user.User;
import com.ll.timemateproject.domain.user.UserRepository;
import com.ll.timemateproject.global.exception.ForbiddenException;
import com.ll.timemateproject.global.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleQueryRepository scheduleQueryRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public List<ScheduleListResponse> getScheduleList(String username, ScheduleListRequest request) {
        // TODO : 예외 처리

        List<ScheduleListResponse> scheduleListByDateAndCategory = scheduleQueryRepository.getScheduleListByDateAndCategory(username,
                request.getStartDate(),
                request.getEndDate(),
                request.getCategoryId());
        log.info(String.valueOf(scheduleListByDateAndCategory.size()));
        return scheduleListByDateAndCategory;
    }

    @Transactional
    public void createSchedule(String name, ScheduleCreateRequest request) {
        User user = userRepository.findByUsername(name).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Schedule schedule = Schedule.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startDatetime(request.getStartDatetime())
                .endDatetime(request.getEndDatetime())
                .location(request.getLocation())
                .user(user)
                .category(category)
                .build();

        if (request.getNotificationTimes() != null && !request.getNotificationTimes().isEmpty()) {
            schedule.addNotifications(request.getNotificationTimes());
        }

        scheduleRepository.save(schedule);
    }

    @Transactional
    public void modifySchedule(String username, Long id, ScheduleModifyRequest request) {
        Schedule schedule = scheduleQueryRepository.getScheduleWithUserById(id).orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
        extracted(username, schedule);

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        schedule.modify(
                category,
                request.getTitle(),
                request.getLocation(),
                request.getDescription(),
                request.getStartDatetime(),
                request.getEndDatetime());
    }

    public void deleteSchedule(String name, Long id) {
        Schedule schedule = getScheduleWithUserById(id);
        extracted(name, schedule);
        scheduleRepository.delete(schedule);
    }

    private static void extracted(String username, Schedule schedule) {
        // 기능 분할 필요
        if(!schedule.getUser().getUsername().equals(username)) {
            throw new ForbiddenException("");
        }
    }

    public Schedule getScheduleWithUserById(Long id) {
        return scheduleQueryRepository.getScheduleWithUserById(id).orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
    }
}
