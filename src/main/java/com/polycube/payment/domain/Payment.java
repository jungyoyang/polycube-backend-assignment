package com.polycube.payment.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

	@Id
	@GeneratedValue
	Long id;

	@OneToOne
	Order order;

	int finalPrice;

	@Enumerated(EnumType.STRING)
	PaymentMethod paymentMethod;

	LocalDateTime paidAt;

}
