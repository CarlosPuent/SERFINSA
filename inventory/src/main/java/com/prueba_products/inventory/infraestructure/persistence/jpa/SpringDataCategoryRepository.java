package com.prueba_products.inventory.infraestructure.persistence.jpa;

import com.prueba_products.inventory.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataCategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCode(String code);
}