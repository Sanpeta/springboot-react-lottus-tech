package com.lottus.tech.processo_seletico_lottus_tech.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottus.tech.processo_seletico_lottus_tech.entity.Product;
import com.lottus.tech.processo_seletico_lottus_tech.repository.ProductRepository;
import com.lottus.tech.processo_seletico_lottus_tech.web.dto.exception.EntityNotFoundExceptionCustom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Product getProductByID(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundExceptionCustom(String.format("Product with id %d not found", id)));
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Void updateProductByID(Long id, Product product) {
        Product productToUpdate = getProductByID(id);

        productToUpdate.setName(product.getName());
        productToUpdate.setCode(product.getCode());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setPrice(product.getPrice());
        
        return null;        
    }
}
