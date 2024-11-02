package com.lottus.tech.processo_seletico_lottus_tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lottus.tech.processo_seletico_lottus_tech.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
