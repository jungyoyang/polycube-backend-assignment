package com.polycube.payment.service;

import lombok.Getter;

@Getter
public class RateDiscountPolicy implements DiscountPolicy {
	int discountRate;
	String policyName;
	int discountAmount;

	public RateDiscountPolicy(int discountRate) {
		this.discountRate = discountRate;
		this.policyName = "RateDiscountPolicy";
	}

	@Override
	public int discount(int originalPrice) {
		return originalPrice * discountRate / 100;
	}
}
