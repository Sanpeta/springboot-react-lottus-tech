package com.lottus.tech.processo_seletico_lottus_tech.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lottus.tech.processo_seletico_lottus_tech.entity.Order;
import com.lottus.tech.processo_seletico_lottus_tech.service.OrderService;
import com.lottus.tech.processo_seletico_lottus_tech.web.dto.mapper.OrderMapper;
import com.lottus.tech.processo_seletico_lottus_tech.web.dto.order.OrderCreateDTO;
import com.lottus.tech.processo_seletico_lottus_tech.web.dto.order.OrderResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Order", description = "Product API")
@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Criar um pedido", description = "Cria um novo pedido", responses = {
        @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponseDTO.class))
        }),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
    })
    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody OrderCreateDTO orderCreateDTO) {
        Order orderCreate = orderService.create(OrderMapper.toOrder(orderCreateDTO));

        return ResponseEntity.status(HttpStatus.CREATED).body(OrderMapper.toDTO(orderCreate));
    }

    @Operation(summary = "Buscar pedido por ID", description = "Retorna o pedido correspondente ao ID fornecido", responses = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponseDTO.class))
        }),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> searchOrderByID(@PathVariable Long id) {
        Order order = orderService.getOrderByID(id);

        return ResponseEntity.status(HttpStatus.OK).body(OrderMapper.toDTO(order));
    }

    @Operation(summary = "Buscar todos os pedidos", description = "Retorna todos os pedidos", responses = {
        @ApiResponse(responseCode = "200", description = "Pedidos encontrados", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponseDTO.class))
        }),
        @ApiResponse(responseCode = "404", description = "Pedidos não encontrados", content = @Content),
    })
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> searchAllOrders() {
        List<Order> orders = orderService.getAllOrders();

        return ResponseEntity.ok().body(OrderMapper.toListDTO(orders));
    }

    @Operation(summary = "Atualizar pedido por ID", description = "Atualiza as informações de um pedido pelo ID", responses = {
        @ApiResponse(responseCode = "204", description = "pedido atualizado com sucesso", content = @Content),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
        @ApiResponse(responseCode = "404", description = "pedido não encontrado", content = @Content),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchOrderByID(@PathVariable Long id,@Valid @RequestBody OrderCreateDTO orderCreateDTO) {
        orderService.updateOrderByID(id, OrderMapper.toOrder(orderCreateDTO));

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletar pedido por ID", description = "Deleta um pedido pelo ID", responses = {
        @ApiResponse(responseCode = "204", description = "pedido deletado com sucesso", content = @Content),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
        @ApiResponse(responseCode = "404", description = "pedido não encontrado", content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderByID(@PathVariable Long id) {
        orderService.deleteOrderByID(id);

        return ResponseEntity.noContent().build();
    }

}
