package com.lottus.tech.processo_seletico_lottus_tech.web.dto.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;

import com.lottus.tech.processo_seletico_lottus_tech.entity.Product;
import com.lottus.tech.processo_seletico_lottus_tech.web.dto.product.ProductCreateDTO;
import com.lottus.tech.processo_seletico_lottus_tech.web.dto.product.ProductResponseDTO;

public class ProductMapper {

    public static Product toProduct(ProductCreateDTO productCreateDTO) {
        return new ModelMapper().map(productCreateDTO, Product.class);
    }

    public static ProductResponseDTO toDTO(Product product) {
        return new ModelMapper().map(product, ProductResponseDTO.class);
    }

    public static List<ProductResponseDTO> toListDTO(List<Product> products) {
        return products.stream().map(product -> toDTO(product)).toList();
    }
}
