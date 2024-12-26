package com.ll.timemateproject.domain.notification;

import com.ll.timemateproject.domain.BaseEntity;
import com.ll.timemateproject.domain.schedule.Schedule;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Column(nullable = false)
    private LocalDateTime notificationTime;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isRead = false;


    public static Notification of(Schedule schedule, LocalDateTime notificationTime) {
        return Notification.builder()
                .schedule(schedule)
                .notificationTime(notificationTime)
                .build();
    }

    public void read() {
        isRead = true;
    }
}
