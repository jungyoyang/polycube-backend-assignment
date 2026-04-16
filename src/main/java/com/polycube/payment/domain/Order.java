package com.polycube.payment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Order {

	@Id
	@GeneratedValue
	Long id;

	String productName;

	int originalPrice;

	@ManyToOne
	Member member;
}
