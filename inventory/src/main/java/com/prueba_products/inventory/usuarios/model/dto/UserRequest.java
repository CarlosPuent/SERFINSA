package com.prueba_products.inventory.usuarios.model.dto;

import com.prueba_products.inventory.usuarios.model.entity.IUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest implements IUser {

    @NotBlank(message = "Username cannot be null or empty")
    private String username;

    @NotBlank(message = "Email cannot be null or empty")
    @Email(message = "Email should be valid")
    private String email;

    private String password;

    private boolean admin;

}