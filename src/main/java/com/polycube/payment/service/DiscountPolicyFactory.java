package com.polycube.payment.service;

import org.springframework.stereotype.Component;

import com.polycube.payment.domain.MemberGrade;

@Component
public class DiscountPolicyFactory {
	public DiscountPolicy getPolicy(MemberGrade grade) {
		switch (grade) {
			case VIP:
				return new FixedDiscountPolicy(1000);
			case VVIP:
				return new RateDiscountPolicy(10);
			default:
				return new FixedDiscountPolicy(0);
		}
	}
}
