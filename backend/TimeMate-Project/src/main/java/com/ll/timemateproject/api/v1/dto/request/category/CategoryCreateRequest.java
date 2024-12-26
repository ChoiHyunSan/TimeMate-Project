package com.ll.timemateproject.api.v1.dto.request.category;

import lombok.Data;

@Data
public class CategoryCreateRequest {
    private String name;
    private String color;
}
