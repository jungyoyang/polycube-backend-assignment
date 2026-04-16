package com.polycube.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polycube.payment.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
