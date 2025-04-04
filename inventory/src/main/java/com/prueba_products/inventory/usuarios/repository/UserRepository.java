package com.prueba_products.inventory.usuarios.repository;

import com.prueba_products.inventory.usuarios.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.username=?1")
    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT u FROM UserEntity u WHERE u.email=?1")
    Optional<UserEntity> findByEmail(String mail);

    Page<UserEntity> findAll(Pageable pageable);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
