package com.ll.timemateproject.api.v1.dto.response.category;

import com.ll.timemateproject.domain.category.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryListResponse {
    private Long id;
    private String name;

    public static CategoryListResponse of(Category category) {
        return CategoryListResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
