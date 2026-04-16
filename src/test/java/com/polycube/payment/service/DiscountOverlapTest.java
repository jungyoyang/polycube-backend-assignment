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
		PointDiscountPolicy pointPolicy = new PointDiscountPolicy();

		//when
		int discountAmount = policy.discount(originalPrice);
		int finalPrice = originalPrice - discountAmount;
		// 포인트 추가 할인
		int pointDiscount = pointPolicy.discount(finalPrice);
		finalPrice -= pointDiscount;

		//then
		assertThat(finalPrice).isEqualTo(8550);
	}

	@Test
	@DisplayName("VVIP + 포인트 결제 시 최종 금액 검증")
	void VVIP_포인트_중복적용() {
		//given
		int originalPrice = 20000;
		RateDiscountPolicy policy = new RateDiscountPolicy(10);
		PointDiscountPolicy pointPolicy = new PointDiscountPolicy();

		//when
		int discountAmount = policy.discount(originalPrice);
		int finalPrice = originalPrice - discountAmount;
		// 포인트 추가 할인
		int pointDiscount = pointPolicy.discount(finalPrice);
		finalPrice -= pointDiscount;

		//then
		assertThat(finalPrice).isEqualTo(17100);
	}


	@Test
	@DisplayName("NORMAL + 포인트 결제 시 최종 금액 검증")
	void NORMAL_포인트_중복적용() {
		//given
		int originalPrice = 10000;
		FixedDiscountPolicy policy = new FixedDiscountPolicy(0);
		PointDiscountPolicy pointPolicy = new PointDiscountPolicy();

		//when
		int discountAmount = policy.discount(originalPrice);
		int finalPrice = originalPrice - discountAmount;
		// 포인트 추가 할인
		int pointDiscount = pointPolicy.discount(finalPrice);
		finalPrice -= pointDiscount;

		//then
		assertThat(finalPrice).isEqualTo(9500);
	}
}
