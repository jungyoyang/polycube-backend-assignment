package com.polycube.payment.service;

public class RateDiscountPolicy implements DiscountPolicy{
	int discountRate ;

	public RateDiscountPolicy(int discountRate) {
		this.discountRate = discountRate;
	}

	@Override
	public int discount(int originalPrice) {
		return originalPrice * discountRate / 100;
	}
}
