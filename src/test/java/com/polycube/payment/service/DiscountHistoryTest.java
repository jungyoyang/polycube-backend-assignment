package com.polycube.payment.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

	@MockBean
	DiscountPolicyFactory discountPolicyFactory;

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


		// when
		// 1.Mock, 1000원 리턴하도록 설정
				when(discountPolicyFactory.getPolicy(MemberGrade.VIP))
					.thenReturn(new FixedDiscountPolicy(1000));

		// 2. 첫 번째 결제
		paymentService.pay(order.getId(), PaymentMethod.CREDIT_CARD);

		// 3. 정책 변경 (IP 할인을 1,000원 → 2,000원으로 변경)
		when(discountPolicyFactory.getPolicy(MemberGrade.VIP))
			.thenReturn(new FixedDiscountPolicy(2000));


		// 4. 새 주문으로 두 번째 결제
		Order order2 = Order.builder()
			.productName("테스트 상품2")
			.originalPrice(10000)
			.member(member)
			.build();
		orderRepository.save(order2);


		paymentService.pay(order2.getId(), PaymentMethod.CREDIT_CARD);


		// then - 이력 조회해서 할인 금액이 1,000원인지 확인
		List<DiscountHistory> histories = discountHistoryRepository.findAll();
		// 첫 번째 이력 → 1000원 그대로
		assertThat(histories.get(0).getDiscountAmount()).isEqualTo(1000);
		assertThat(histories.get(0).getPolicyName()).isEqualTo("FixedDiscountPolicy");
		// 두 번째 이력 → 2000원 (정책 변경 후 결제)
		assertThat(histories.get(1).getDiscountAmount()).isEqualTo(2000);
	}
}
