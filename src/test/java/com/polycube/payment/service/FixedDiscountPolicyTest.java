package com.polycube.payment.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FixedDiscountPolicyTest {

	@Test
	@DisplayName("VIP 회원은 1,000원 고정 할인이 적용")
	void VIP_고정할인_적용() {
		//given
		FixedDiscountPolicy policy =new FixedDiscountPolicy(1000);

		//when
		int result = policy.discount(10000);

		//then
		assertThat(result).isEqualTo(1000);
	}

	@Test
	@DisplayName("NORMAL 회원은 할인이 적용되지 않는다")
	void NORMAL_할인_없음() {
		//given
		FixedDiscountPolicy policy =new FixedDiscountPolicy(0);

		//when
		int result = policy.discount(10000);

		//then
		assertThat(result).isEqualTo(0);
	}

	@Test
	@DisplayName("할인 금액이 원가보다 크면 원가만큼만 할인한다")
	void 할인금액_원가초과_방어() {
		//given
		FixedDiscountPolicy policy =new FixedDiscountPolicy(1000);

		//when
		int result = policy.discount(800);

		//then
		assertThat(result).isEqualTo(800);
	}
}
