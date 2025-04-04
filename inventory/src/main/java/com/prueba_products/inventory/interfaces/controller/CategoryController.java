package com.prueba_products.inventory.interfaces.controller;

import com.prueba_products.inventory.application.useCases.CategoryUseCase;
import com.prueba_products.inventory.domain.model.Category;
import com.prueba_products.inventory.interfaces.dto.CategoryRequest;
import com.prueba_products.inventory.interfaces.dto.CategoryResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryUseCase categoryUseCase;

    public CategoryController(CategoryUseCase categoryUseCase) {
        this.categoryUseCase = categoryUseCase;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> response = categoryUseCase.getAllCategories()
                .stream()
                .map(this::toCategoryResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(toCategoryResponse(categoryUseCase.getCategoryById(id)));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        Category created = categoryUseCase.createCategory(toCategoryEntity(request));
        return ResponseEntity.created(URI.create("/api/categories/" + created.getId()))
                .body(toCategoryResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id,
                                                           @Valid @RequestBody CategoryRequest request) {
        Category updated = categoryUseCase.updateCategory(id, toCategoryEntity(request));
        return ResponseEntity.ok(toCategoryResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryUseCase.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    //los mappers
    private CategoryResponse toCategoryResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setCode(category.getCode());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        return response;
    }

    private Category toCategoryEntity(CategoryRequest request) {
        Category category = new Category();
        category.setCode(request.getCode());
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return category;
    }
}
