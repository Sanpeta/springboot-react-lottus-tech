package com.lottus.tech.processo_seletico_lottus_tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lottus.tech.processo_seletico_lottus_tech.entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    void deleteByOrderId(Long orderId);

}
