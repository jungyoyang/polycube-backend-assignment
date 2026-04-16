package com.polycube.payment.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RateDiscountPolicyTest {

	@Test
	@DisplayName("VVIP 회원은 10% 정률 할인이 적용")
	void VVIP_정률할인_적용() {
		//given
		RateDiscountPolicy policy = new RateDiscountPolicy(10);

		//when
		int result = policy.discount(10000);

		//then
		assertThat(result).isEqualTo(1000);
	}
	@Test
	@DisplayName("주문 금액이 0원이면 할인 금액도 0원이다")
	void VVIP_0원일때_엣지케이스() {
		//given
		RateDiscountPolicy policy = new RateDiscountPolicy(10);

		//when
		int result = policy.discount(0);

		//then
		assertThat(result).isEqualTo(0);
	}

}
