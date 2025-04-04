package com.prueba_products.inventory.application.useCases;

import com.prueba_products.inventory.domain.model.Product;
import com.prueba_products.inventory.domain.repository.ProductRepository;
import com.prueba_products.inventory.exceptions.DuplicateResourceException;
import com.prueba_products.inventory.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductUseCase {

    private final ProductRepository productRepository;

    public ProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> getProductByCode(String code) {
        return productRepository.findByCode(code);
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Product createProduct(Product product) {
        if (product.getCode() == null || product.getCode().isBlank()) {
            throw new IllegalArgumentException("El código del producto es obligatorio.");
        }

        if (productRepository.findByCode(product.getCode()).isPresent()) {
            throw new DuplicateResourceException("Ya existe un producto con el código: " + product.getCode());
        }

        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio del producto debe ser mayor que cero.");
        }

        if (product.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }

        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio.");
        }

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        if (updatedProduct.getCode() == null || updatedProduct.getCode().isBlank()) {
            throw new IllegalArgumentException("El código del producto es obligatorio.");
        }

        Optional<Product> productByCode = productRepository.findByCode(updatedProduct.getCode());
        if (productByCode.isPresent() && !productByCode.get().getId().equals(id)) {
            throw new DuplicateResourceException("Ya existe otro producto con el código: " + updatedProduct.getCode());
        }

        if (updatedProduct.getPrice() == null || updatedProduct.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero.");
        }

        if (updatedProduct.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }

        if (updatedProduct.getName() == null || updatedProduct.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio.");
        }

        existingProduct.setCode(updatedProduct.getCode());
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStock(updatedProduct.getStock());
        existingProduct.setCategories(updatedProduct.getCategories());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }
        productRepository.deleteById(id);
    }
}
