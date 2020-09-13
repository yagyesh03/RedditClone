package com.mr.rc.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mr.rc.springjwt.models.MailVerificationToken;

public interface MailVerificationTokenRepo extends JpaRepository<MailVerificationToken, Long> {

	MailVerificationToken findByToken(String token);
}
