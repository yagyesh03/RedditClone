package com.mr.rc.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mr.rc.springjwt.models.PasswordResetToken;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {

	PasswordResetToken findByToken(String token);

}
