package com.polycube.payment.service;

public class FixedDiscountPolicy implements DiscountPolicy{
	int discountAmount;

	public FixedDiscountPolicy(int discountAmount) {
		this.discountAmount = discountAmount;
	}

	@Override
	public int discount(int originalPrice) {
		return discountAmount;
	}
}
