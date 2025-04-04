package com.prueba_products.inventory.usuarios.mapper;

import com.prueba_products.inventory.usuarios.model.dto.UserResponse;
import com.prueba_products.inventory.usuarios.model.entity.UserEntity;
import java.util.stream.Collectors;

public class DtoMapperUser {

    public static UserResponse map(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream()
                        .anyMatch(role -> "ROLE_ADMIN".equals(role.getName())), // <-- esto llena el campo admin
                user.getRoles().stream()
                        .map(role -> role.getName())
                        .collect(Collectors.toList())
        );
    }
}

