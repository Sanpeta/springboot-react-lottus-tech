package com.lottus.tech.processo_seletico_lottus_tech.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Tag(name = "Product", description = "Product API")
@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Criar um produto", description = "Cria um novo produto", responses = {
        @ApiResponse(responseCode = "201", description = "Produto criado com sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))
        }),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
    })
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductCreateDTO productCreateDTO) {
        Product productCreate = productService.create(ProductMapper.toProduct(productCreateDTO));

        return ResponseEntity.status(HttpStatus.CREATED).body(ProductMapper.toDTO(productCreate));
    }

    @Operation(summary = "Buscar produto por ID", description = "Retorna o produto correspondente ao ID fornecido", responses = {
        @ApiResponse(responseCode = "200", description = "Produto encontrado", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))
        }),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> searchProductByID(@PathVariable Long id) {
        Product product = productService.getProductByID(id);

        return ResponseEntity.ok().body(ProductMapper.toDTO(product));
    }

    @Operation(summary = "Buscar todos os produtos", description = "Retorna uma lista com todos os produtos", responses = {
        @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))
        }),
    })
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> searchAllProducts() {
        List<Product> products = productService.getAllProducts();

        return ResponseEntity.ok().body(ProductMapper.toListDTO(products));
    }

    @Operation(summary = "Atualizar produto por ID", description = "Atualiza as informações de um produto pelo ID", responses = {
        @ApiResponse(responseCode = "204", description = "Produto atualizado com sucesso", content = @Content),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchProductByID(@PathVariable Long id, @RequestBody Product product) {
        productService.updateProductByID(id, product);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletar produto por ID", description = "Deleta um produto pelo ID", responses = {
        @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso", content = @Content),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductByID(@PathVariable Long id) {
        productService.deleteProductByID(id);

        return ResponseEntity.noContent().build();
    }

}
