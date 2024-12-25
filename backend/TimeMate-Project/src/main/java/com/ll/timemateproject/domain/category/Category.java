package com.ll.timemateproject.domain.category;

import com.ll.timemateproject.domain.BaseEntity;
import com.ll.timemateproject.domain.schedule.Schedule;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 7)
    private String color;

    @OneToMany(mappedBy = "category")
    private List<Schedule> schedules = new ArrayList<>();
}
