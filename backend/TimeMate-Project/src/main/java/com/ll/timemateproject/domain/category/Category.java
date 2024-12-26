package com.ll.timemateproject.domain.category;

import com.ll.timemateproject.domain.BaseEntity;
import com.ll.timemateproject.domain.schedule.Schedule;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 7)
    private String color;

    @Builder.Default
    @OneToMany(mappedBy = "category")
    private List<Schedule> schedules = new ArrayList<>();

    public static Category of(String name, String color) {
        return Category.builder()
                .name(name)
                .color(color)
                .build();
    }

    public void modify(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
