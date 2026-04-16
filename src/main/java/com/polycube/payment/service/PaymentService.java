package com.polycube.payment.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.polycube.payment.domain.Order;
import com.polycube.payment.domain.Payment;
import com.polycube.payment.domain.PaymentMethod;
import com.polycube.payment.repository.OrderRepository;
import com.polycube.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
	private final PaymentRepository paymentRepository;
	private final DiscountPolicyFactory discountPolicyFactory;
	private final OrderRepository orderRepository;

	public Payment pay(Long orderId, PaymentMethod paymentMethod) {
		// 0. orderId로 Order 조회
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

		// 1. 할인 금액 계산
		DiscountPolicy policy = discountPolicyFactory.getPolicy(order.getMember().getGrade());
		int discountAmount = policy.discount(order.getOriginalPrice());

		// 2. 최종 결제 금액 계산
		int finalPrice = order.getOriginalPrice() - discountAmount;

		// 3. Payment 만들어서 저장
		Payment payment = Payment.builder()
			.order(order)
			.finalPrice(finalPrice).paymentMethod(paymentMethod)
			.paidAt(LocalDateTime.now()).build();

		return paymentRepository.save(payment);
	}
}