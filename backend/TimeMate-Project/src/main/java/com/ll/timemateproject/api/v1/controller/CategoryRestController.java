package com.ll.timemateproject.api.v1.controller;

import com.ll.timemateproject.api.v1.dto.Result;
import com.ll.timemateproject.api.v1.dto.request.category.CategoryCreateRequest;
import com.ll.timemateproject.api.v1.dto.response.category.CategoryListResponse;
import com.ll.timemateproject.domain.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping
    public Result<List<CategoryListResponse>> getCategoryList(){
        return Result.success(categoryService.getCategoryList());
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Result<Void> addCategory(@RequestBody CategoryCreateRequest request){
        categoryService.createCategory(request);
        return Result.success(null);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Result<Void> updateCategory(@RequestBody CategoryCreateRequest request,
                                       @PathVariable Long id){
        categoryService.modifyCategory(id, request);
        return Result.success(null);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Result<Void> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return Result.success(null);
    }
}
