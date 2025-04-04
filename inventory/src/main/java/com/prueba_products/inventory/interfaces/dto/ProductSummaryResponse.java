package com.prueba_products.inventory.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSummaryResponse {

    private Long id;
    private String code;
    private String name;
    private Double price;
    private int stock;
}
