package com.prueba_products.inventory.usuarios.controller;

import com.prueba_products.inventory.exceptions.ResourceNotFoundException;
import com.prueba_products.inventory.usuarios.model.dto.UserRequest;
import com.prueba_products.inventory.usuarios.model.dto.UserResponse;
import com.prueba_products.inventory.usuarios.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "User Management", description = "APIs for managing users")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin(originPatterns = "*")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get current authenticated user", description = "Returns the logged in user's info")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(java.security.Principal principal) {
        String username = principal.getName();
        UserResponse user = userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get all users", description = "Returns a list of all users")
    @GetMapping
    public ResponseEntity<List<UserResponse>> list() {

        List<UserResponse> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get user by ID", description = "Returns a user by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> show(@PathVariable Long id) {
        UserResponse user = userService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario buscado no existe"));
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Create a new user", description = "Creates a new user")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserRequest user, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(validation(result));
        }
        UserResponse createdUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "Update a user", description = "Updates an existing user")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UserRequest user, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(validation(result));
        }
        UserResponse updatedUser = userService.update(user, id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario buscado no existe"));
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete a user", description = "Deletes a user by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        userService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario buscado no existe"));
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Map<String, String> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err ->
                errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage())
        );
        return errors;
    }
}

