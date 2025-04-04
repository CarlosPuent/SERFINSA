package com.prueba_products.inventory.application.useCases;

import com.prueba_products.inventory.domain.model.Category;
import com.prueba_products.inventory.domain.repository.CategoryRepository;
import com.prueba_products.inventory.exceptions.DuplicateResourceException;
import com.prueba_products.inventory.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryUseCase {

    private final CategoryRepository categoryRepository;

    public CategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
    }

    public Category createCategory(Category category) {
        if (category.getCode() == null || category.getCode().isBlank()) {
            throw new IllegalArgumentException("El código de la categoría es obligatorio.");
        }

        if (categoryRepository.findByCode(category.getCode()).isPresent()) {
            throw new DuplicateResourceException("Ya existe una categoría con el código: " + category.getCode());
        }

        if (category.getName() == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));

        if (updatedCategory.getCode() == null || updatedCategory.getCode().isBlank()) {
            throw new IllegalArgumentException("El código es obligatorio.");
        }

        Optional<Category> existingByCode = categoryRepository.findByCode(updatedCategory.getCode());
        if (existingByCode.isPresent() && !existingByCode.get().getId().equals(id)) {
            throw new DuplicateResourceException("Ya existe otra categoría con el código: " + updatedCategory.getCode());
        }

        if (updatedCategory.getName() == null || updatedCategory.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        existing.setCode(updatedCategory.getCode());
        existing.setName(updatedCategory.getName());
        existing.setDescription(updatedCategory.getDescription());

        return categoryRepository.save(existing);
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría no encontrada con ID: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
