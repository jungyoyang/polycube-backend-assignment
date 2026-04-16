package com.polycube.payment.service;

public interface DiscountPolicy {
	int discount(int originalPrice);
	String getPolicyName();
	int getDiscountRate();
}
