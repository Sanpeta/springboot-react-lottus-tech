package com.lottus.tech.processo_seletico_lottus_tech.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottus.tech.processo_seletico_lottus_tech.entity.Order;
import com.lottus.tech.processo_seletico_lottus_tech.repository.OrderProductRepository;
import com.lottus.tech.processo_seletico_lottus_tech.repository.OrderRepository;
import com.lottus.tech.processo_seletico_lottus_tech.web.dto.exception.EntityNotFoundExceptionCustom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductsRepository;

    @Transactional
    public Order create(Order order) {
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Order getOrderByID(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundExceptionCustom(String.format("Order with id %d not found", id)));
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Void updateOrderByID(Long id, Order order) {
        Order orderToUpdate = getOrderByID(id);

        orderToUpdate.setClientName(order.getClientName());
        orderToUpdate.setCode(order.getCode());
        orderToUpdate.setDate(order.getDate());
        orderToUpdate.setTotalItems(order.getTotalItems());
        orderToUpdate.setTotalValue(order.getTotalValue());

        return null;
    }

    @Transactional
    public Void deleteOrderByID(Long id) {
        orderProductsRepository.deleteByOrderId(id);

        orderRepository.deleteById(id);

        return null;
    }

}
