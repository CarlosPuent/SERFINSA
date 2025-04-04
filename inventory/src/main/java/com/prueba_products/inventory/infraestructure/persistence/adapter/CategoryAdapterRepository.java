package com.prueba_products.inventory.infraestructure.persistence.adapter;

import com.prueba_products.inventory.domain.model.Category;
import com.prueba_products.inventory.domain.repository.CategoryRepository;
import com.prueba_products.inventory.infraestructure.persistence.jpa.SpringDataCategoryRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryAdapterRepository implements CategoryRepository {

    private final SpringDataCategoryRepository springDataCategoryRepository;

    public CategoryAdapterRepository(SpringDataCategoryRepository springDataCategoryRepository) {
        this.springDataCategoryRepository = springDataCategoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return springDataCategoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return springDataCategoryRepository.findById(id);
    }

    @Override
    public Optional<Category> findByCode(String code) {
        return springDataCategoryRepository.findByCode(code);
    }

    @Override
    public Category save(Category category) {
        return springDataCategoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        springDataCategoryRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return springDataCategoryRepository.existsById(id);
    }
}
