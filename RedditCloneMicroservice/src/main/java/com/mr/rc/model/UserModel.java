package com.mr.rc.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserModel {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(name = "fn")
	@Size(max = 10)
	private String firstName;
	
	@NotBlank
	@Column(name = "ln")
	@Size(max = 10)
	private String lastName;

	@NotBlank
	@Size(max = 50)
	@Email
	@Column(name = "email")
	private String email;
	
	@NotBlank
	@Size(max = 10)
	@Column(name = "mobile")
	private String mobile;
	
	@NotBlank
	@Column(name = "username")
	@Size(min = 3, max = 20)
	private String username;

	@NotBlank
	@Column(name = "pass")
	@Size(min = 6, max = 90)
	private String password;
	

	@Column(name = "mvt")
	private boolean mailVerfication;


	public UserModel() {
	}

	public UserModel(String firstName, String lastName, String email, String mobile, String username,  String password, boolean mailVerification) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobile = mobile;
		this.username = username;
		this.password = password;
		this.mailVerfication = mailVerification;
	}
	
	

	public boolean isMailVerfication() {
		return mailVerfication;
	}

	public void setMailVerfication(boolean mailVerfication) {
		this.mailVerfication = mailVerfication;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}