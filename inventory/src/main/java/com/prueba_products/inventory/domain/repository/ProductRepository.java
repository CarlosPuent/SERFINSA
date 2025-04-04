package com.prueba_products.inventory.domain.repository;

import com.prueba_products.inventory.domain.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();
    Optional<Product> findById(Long id);
    Optional<Product> findByCode(String code);
    List<Product> findByNameContainingIgnoreCase(String name);
    Product save(Product product);
    void deleteById(Long id);
    boolean existsById(Long id);
}
