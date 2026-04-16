package com.polycube.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polycube.payment.domain.DiscountHistory;

public interface DiscountHistoryRepository extends JpaRepository<DiscountHistory, Long>{
}
