package com.prueba_products.inventory.interfaces.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "El código es obligatorio")
    private String code;
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    private String description;
}
