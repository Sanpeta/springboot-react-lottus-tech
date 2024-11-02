package com.lottus.tech.processo_seletico_lottus_tech.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lottus.tech.processo_seletico_lottus_tech.entity.Product;
import com.lottus.tech.processo_seletico_lottus_tech.service.ProductService;
import com.lottus.tech.processo_seletico_lottus_tech.web.dto.mapper.ProductMapper;
import com.lottus.tech.processo_seletico_lottus_tech.web.dto.product.ProductCreateDTO;
import com.lottus.tech.processo_seletico_lottus_tech.web.dto.product.ProductResponseDTO;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductCreateDTO productCreateDTO) {
        Product productCreate = productService.create(ProductMapper.toProduct(productCreateDTO));

        return ResponseEntity.status(HttpStatus.CREATED).body(ProductMapper.toDTO(productCreate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> searchProductByID(@PathVariable Long id) {
        Product product = productService.getProductByID(id);

        return ResponseEntity.ok().body(ProductMapper.toDTO(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> searchAllProducts() {
        List<Product> products = productService.getAllProducts();

        return ResponseEntity.ok().body(ProductMapper.toListDTO(products));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchProductByID(@PathVariable Long id, @RequestBody Product product) {
        productService.updateProductByID(id, product);

        return ResponseEntity.noContent().build();
    }

}
