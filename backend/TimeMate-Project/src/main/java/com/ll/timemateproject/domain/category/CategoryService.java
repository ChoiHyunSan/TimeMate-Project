package com.ll.timemateproject.domain.category;

import com.ll.timemateproject.api.v1.dto.request.category.CategoryCreateRequest;
import com.ll.timemateproject.api.v1.dto.response.category.CategoryListResponse;
import com.ll.timemateproject.global.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public List<CategoryListResponse> getCategoryList() {
        return categoryRepository.findAll().stream()
                .map(CategoryListResponse::of)
                .toList();
    }

    @Transactional
    public void createCategory(CategoryCreateRequest request) {
        categoryRepository.save(Category.of(request.getName(), request.getColor()));
    }

    @Transactional
    public void modifyCategory(Long id, CategoryCreateRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        category.modify(request.getName(), request.getColor());
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
