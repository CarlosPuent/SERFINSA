package com.prueba_products.inventory.interfaces.controller;

import com.prueba_products.inventory.application.useCases.ProductUseCase;
import com.prueba_products.inventory.domain.model.Product;
import com.prueba_products.inventory.interfaces.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductUseCase productUseCase;

    public ProductController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> responses = productUseCase.getAllProducts()
                .stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return productUseCase.getProductById(id)
                .map(product -> ResponseEntity.ok(toProductResponse(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ProductResponse> getProductByCode(@PathVariable String code) {
        return productUseCase.getProductByCode(code)
                .map(product -> ResponseEntity.ok(toProductResponse(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        Product created = productUseCase.createProduct(toProductEntity(request));
        return ResponseEntity.created(URI.create("/api/products/" + created.getId()))
                .body(toProductResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        Product updated = productUseCase.updateProduct(id, toProductEntity(request));
        return ResponseEntity.ok(toProductResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    //Aca van los métodos de mapeo entre entidad y DTO´s
    private ProductResponse toProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setCode(product.getCode());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());

        List<CategoryResponse> categoryResponses = product.getCategories().stream()
                .map(cat -> {
                    CategoryResponse cr = new CategoryResponse();
                    cr.setId(cat.getId());
                    cr.setCode(cat.getCode());
                    cr.setName(cat.getName());
                    cr.setDescription(cat.getDescription());
                    return cr;
                }).collect(Collectors.toList());

        response.setCategories(categoryResponses);
        return response;
    }

    private Product toProductEntity(ProductRequest request) {
        Product product = new Product();
        product.setCode(request.getCode());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        List<Long> categoryIds = request.getCategoryIds();
        List<com.prueba_products.inventory.domain.model.Category> categories = categoryIds.stream().map(id -> {
            com.prueba_products.inventory.domain.model.Category c = new com.prueba_products.inventory.domain.model.Category();
            c.setId(id);
            return c;
        }).collect(Collectors.toList());

        product.setCategories(categories);
        return product;
    }
}
