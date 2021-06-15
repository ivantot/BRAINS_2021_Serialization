package com.iktakademija.serialization.project.controllers.dto;

import java.time.LocalDate;

public class UserRegisterDTO {

	private String name;
	private String email;
	private LocalDate dateOfBirth;
	private String password;

	public UserRegisterDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBIrth) {
		this.dateOfBirth = dateOfBIrth;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
