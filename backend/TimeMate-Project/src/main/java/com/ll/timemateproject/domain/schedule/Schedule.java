package com.ll.timemateproject.domain.schedule;

import com.ll.timemateproject.domain.BaseEntity;
import com.ll.timemateproject.domain.category.Category;
import com.ll.timemateproject.domain.notification.Notification;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

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
}
