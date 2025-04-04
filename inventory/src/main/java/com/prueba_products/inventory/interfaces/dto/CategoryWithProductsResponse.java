package com.prueba_products.inventory.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryWithProductsResponse {

    private Long id;
    private String code;
    private String name;
    private String description;
    private List<ProductSummaryResponse> products;
}
