package com.prueba_products.inventory.infraestructure.persistence.adapter;

import com.prueba_products.inventory.domain.model.Product;
import com.prueba_products.inventory.domain.repository.ProductRepository;
import com.prueba_products.inventory.infraestructure.persistence.jpa.SpringDataProductRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductAdapterRepository implements ProductRepository {

    private final SpringDataProductRepository springDataProductRepository;

    public ProductAdapterRepository(SpringDataProductRepository springDataProductRepository) {
        this.springDataProductRepository = springDataProductRepository;
    }

    @Override
    public List<Product> findAll() {
        return springDataProductRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return springDataProductRepository.findById(id);
    }

    @Override
    public Optional<Product> findByCode(String code) {
        return springDataProductRepository.findByCode(code);
    }

    @Override
    public List<Product> findByNameContainingIgnoreCase(String name) {
        return springDataProductRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Product save(Product product) {
        return springDataProductRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        springDataProductRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return springDataProductRepository.existsById(id);
    }
}
