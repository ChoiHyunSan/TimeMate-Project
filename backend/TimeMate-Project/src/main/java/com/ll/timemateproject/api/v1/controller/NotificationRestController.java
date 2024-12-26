package com.ll.timemateproject.api.v1.controller;

import com.ll.timemateproject.api.v1.dto.Result;
import com.ll.timemateproject.api.v1.dto.response.notification.NotificationListResponse;
import com.ll.timemateproject.domain.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationRestController {

    private final NotificationService notificationService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public Result<List<NotificationListResponse>> getNotifications(Principal principal) {
        return Result.success(notificationService.getNotificationList(principal.getName()));
    }

    @PutMapping("/{id}/read")
    @PreAuthorize("isAuthenticated()")
    public Result<Void> markAsRead(@PathVariable Long id, Principal principal) {
        notificationService.markAsRead(principal.getName(), id);
        return Result.success(null);
    }
}
