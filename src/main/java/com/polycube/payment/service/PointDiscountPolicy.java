package com.polycube.payment.service;

import com.polycube.payment.service.DiscountPolicy;

import lombok.Getter;

@Getter
public class PointDiscountPolicy implements DiscountPolicy {
	String policyName = "PointDiscountPolicy";
	int discountRate = 5;
	int discountAmount;

	@Override
	public int discount(int originalPrice) {
		return originalPrice * discountRate / 100;

	}
}

