package com.polycube.payment.service;

import lombok.Getter;

@Getter
public class FixedDiscountPolicy implements DiscountPolicy{
	int discountAmount;
	String policyName;
	int discountRate;

	public FixedDiscountPolicy(int discountAmount) {
		this.discountAmount = discountAmount;
		this.policyName = "FixedDiscountPolicy";
		this.discountRate = 0;  // 고정 할인이라 비율은 0

	}

	@Override
	public int discount(int originalPrice) {
		return Math.min(discountAmount, originalPrice);	}
}
