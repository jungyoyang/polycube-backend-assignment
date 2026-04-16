package com.polycube.payment.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polycube.payment.domain.Payment;
import com.polycube.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {
	private final	PaymentService paymentService;

	@PostMapping
	public Payment pay(@RequestBody PaymentRequest request) {
		return paymentService.pay(request.getOrderId(), request.getPaymentMethod());
	}
}
