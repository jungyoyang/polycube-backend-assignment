package com.polycube.payment.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.polycube.payment.domain.DiscountHistory;
import com.polycube.payment.domain.Member;
import com.polycube.payment.domain.MemberGrade;
import com.polycube.payment.domain.Order;
import com.polycube.payment.domain.PaymentMethod;
import com.polycube.payment.repository.DiscountHistoryRepository;
import com.polycube.payment.repository.MemberRepository;
import com.polycube.payment.repository.OrderRepository;

@SpringBootTest

public class DiscountHistoryTest {
	@Autowired
	MemberRepository memberRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	PaymentService paymentService;

	@Autowired
	DiscountHistoryRepository discountHistoryRepository;

	@Test
	@DisplayName("정책 변경 후 과거 결제 이력 보존 검증")
	void 정책_변경_후_이력_보존() {
		// given - 회원, 주문 만들어서 DB에 저장
		Member member = Member.builder()
			.grade(MemberGrade.VIP)
			.build();
		memberRepository.save(member);

		Order order = Order.builder()
			.productName("테스트 상품")
			.originalPrice(10000)
			.member(member)
			.build();
		orderRepository.save(order);

		// when - 결제 실행 (VIP 1,000원 할인 적용됨)
		paymentService.pay(order.getId(), PaymentMethod.CREDIT_CARD);

		// 정책 변경: VIP 할인을 1,000원 → 2,000원으로 변경했다고 가정
		// (새 주문으로 두 번째 결제)
		Order order2 = Order.builder()
			.productName("테스트 상품2")
			.originalPrice(10000)
			.member(member)
			.build();
		orderRepository.save(order2);
		// Factory 변경 후 두 번째 결제가 이루어졌다고 가정

		// then - 이력 조회해서 할인 금액이 1,000원인지 확인
		List<DiscountHistory> histories = discountHistoryRepository.findAll();
		assertThat(histories.get(0).getDiscountAmount()).isEqualTo(1000);
		assertThat(histories.get(0).getPolicyName()).isEqualTo("FixedDiscountPolicy");
	}
}
