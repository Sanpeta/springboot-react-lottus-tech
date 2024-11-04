package com.lottus.tech.processo_seletico_lottus_tech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lottus.tech.processo_seletico_lottus_tech.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.code = :code")
    Optional<Product> findByCode(@Param("code") String code);

}
