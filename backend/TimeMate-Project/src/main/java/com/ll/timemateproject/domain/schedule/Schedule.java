package com.ll.timemateproject.domain.schedule;

import com.ll.timemateproject.domain.BaseEntity;
import com.ll.timemateproject.domain.category.Category;
import com.ll.timemateproject.domain.notification.Notification;
import com.ll.timemateproject.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime startDatetime;

    @Column(nullable = false)
    private LocalDateTime endDatetime;

    @Column(length = 200)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(length = 20)
    private String repeatType;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<>();

    public void modify(Category category, String title, String location, String description, LocalDateTime startDatetime, LocalDateTime endDatetime) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.location = location;
    }

    public void addNotifications(List<LocalDateTime> notificationTimes) {
        if (this.notifications == null) {
            this.notifications = new ArrayList<>();
        }
        notificationTimes.forEach(time -> {
            Notification notification = Notification.of(this, time);
            notifications.add(notification);
        });
    }
}
