package com.polycube.payment.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DiscountOverlapTest {

	@Test
	@DisplayName("VIP + 포인트 결제 시 최종 금액 검증")
	void VIP_포인트_중복적용() {
		//given
		int originalPrice = 10000;
		FixedDiscountPolicy policy = new FixedDiscountPolicy(1000);

		//when
		int discountAmount = policy.discount(originalPrice);
		int finalPrice = originalPrice - discountAmount;
		// 포인트 추가 할인
		int pointDiscount = finalPrice * 5 / 100;
		finalPrice -= pointDiscount;

		//then
		assertThat(finalPrice).isEqualTo(8550);
	}
}
