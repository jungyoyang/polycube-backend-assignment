package com.polycube.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polycube.payment.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
