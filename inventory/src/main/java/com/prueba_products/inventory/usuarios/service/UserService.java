package com.prueba_products.inventory.usuarios.service;

import com.prueba_products.inventory.usuarios.model.dto.UserRequest;
import com.prueba_products.inventory.usuarios.model.dto.UserResponse;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserResponse> findAll();
    Optional<UserResponse> findById(Long id);
    UserResponse save(UserRequest userRequest);
    Optional<UserResponse> update(UserRequest userRequest, Long id);
    void deleteById(Long id);
    Optional<UserResponse> findByUsername(String username);

}