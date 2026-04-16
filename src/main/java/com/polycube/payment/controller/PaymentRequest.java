package com.polycube.payment.controller;

import com.polycube.payment.domain.PaymentMethod;

import lombok.Getter;

@Getter
public class PaymentRequest {
	private Long orderId;
	private PaymentMethod paymentMethod;
}
