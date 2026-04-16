package com.polycube.payment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountHistory {

	@Id
	@GeneratedValue
	Long id;

	@Enumerated(EnumType.STRING)
	MemberGrade grade;

	String policyName;

	int discountRate;

	int discountAmount;

	@ManyToOne
	Payment payment;

}
