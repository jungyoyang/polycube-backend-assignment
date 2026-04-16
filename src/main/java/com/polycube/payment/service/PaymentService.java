package com.polycube.payment.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.polycube.payment.domain.DiscountHistory;
import com.polycube.payment.domain.Order;
import com.polycube.payment.domain.Payment;
import com.polycube.payment.domain.PaymentMethod;
import com.polycube.payment.repository.DiscountHistoryRepository;
import com.polycube.payment.repository.OrderRepository;
import com.polycube.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
	private final PaymentRepository paymentRepository;
	private final DiscountPolicyFactory discountPolicyFactory;
	private final OrderRepository orderRepository;
	private final DiscountHistoryRepository discountHistoryRepository;

	public Payment pay(Long orderId, PaymentMethod paymentMethod) {

		// 0. orderId로 Order 조회
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

		// 1. 할인 금액 계산
		DiscountPolicy policy = discountPolicyFactory.getPolicy(order.getMember().getGrade());
		int discountAmount = policy.discount(order.getOriginalPrice());

		// 2. 최종 결제 금액 계산
		int finalPrice = order.getOriginalPrice() - discountAmount;

		// 2-1. 포인트 결제 시 추가 5% 할인
		int pointDiscount = 0;
		if (paymentMethod == PaymentMethod.POINT) {
			// finalPrice에서 5% 추가 할인
			PointDiscountPolicy pointPolicy = new PointDiscountPolicy();
			pointDiscount = pointPolicy.discount(finalPrice);
			finalPrice -= pointDiscount;
		}

		// 3. Payment 만들어서 저장
		Payment payment = Payment.builder()
			.order(order)
			.finalPrice(finalPrice).paymentMethod(paymentMethod)
			.paidAt(LocalDateTime.now()).build();
		paymentRepository.save(payment);

		// 4. 등급할인 이력 저장
		String policyName = policy.getPolicyName();
		int discountRate = policy.getDiscountRate();
		DiscountHistory history = DiscountHistory.builder()
			.grade(order.getMember().getGrade())
			.policyName(policyName)
			.discountRate(discountRate)
			.discountAmount(discountAmount)
			.payment(payment)
			.build();
		discountHistoryRepository.save(history);

		//5. 포인트 할인 이력 저장
		if (pointDiscount > 0) {
			DiscountHistory pointHistory = DiscountHistory.builder()
				.grade(order.getMember().getGrade())
				.policyName("PointDiscountPolicy")
				.discountRate(5)
				.discountAmount(pointDiscount)
				.payment(payment)
				.build();
			discountHistoryRepository.save(pointHistory);
		}

		return payment;
	}
}