package com.prueba_products.inventory.infraestructure.persistence.jpa;

import com.prueba_products.inventory.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface SpringDataProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByCode(String code);

    List<Product> findByNameContainingIgnoreCase(String name);
}

