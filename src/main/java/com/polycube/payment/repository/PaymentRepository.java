package com.polycube.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polycube.payment.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
