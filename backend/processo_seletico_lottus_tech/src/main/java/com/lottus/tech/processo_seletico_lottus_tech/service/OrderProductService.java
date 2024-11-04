package com.lottus.tech.processo_seletico_lottus_tech.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottus.tech.processo_seletico_lottus_tech.entity.OrderProduct;
import com.lottus.tech.processo_seletico_lottus_tech.repository.OrderProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;

    @Transactional
    public OrderProduct addOrderProduct(OrderProduct orderProduct) {

        return orderProductRepository.save(orderProduct);
    }

    @Transactional(readOnly = true)
    public List<OrderProduct> getAllOrderProducts() {
        return orderProductRepository.findAll();
    }
}
