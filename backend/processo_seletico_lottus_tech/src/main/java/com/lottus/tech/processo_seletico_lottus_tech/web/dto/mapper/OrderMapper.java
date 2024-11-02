package com.lottus.tech.processo_seletico_lottus_tech.web.dto.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;

import com.lottus.tech.processo_seletico_lottus_tech.entity.Order;
import com.lottus.tech.processo_seletico_lottus_tech.web.dto.order.OrderCreateDTO;
import com.lottus.tech.processo_seletico_lottus_tech.web.dto.order.OrderResponseDTO;

public class OrderMapper {

    public static Order toOrder(OrderCreateDTO orderCreateDTO) {
        return new ModelMapper().map(orderCreateDTO, Order.class);
    }

    public static OrderResponseDTO toDTO(Order order) {
        return new ModelMapper().map(order, OrderResponseDTO.class);
    }

    public static List<OrderResponseDTO> toListDTO(List<Order> orders) {
        return orders.stream().map(order -> toDTO(order)).toList();
    }

}
