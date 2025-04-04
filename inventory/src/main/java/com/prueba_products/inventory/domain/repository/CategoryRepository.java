package com.prueba_products.inventory.domain.repository;

import com.prueba_products.inventory.domain.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    List<Category> findAll();
    Optional<Category> findById(Long id);
    Optional<Category> findByCode(String code);
    Category save(Category category);
    void deleteById(Long id);
    boolean existsById(Long id);
}
