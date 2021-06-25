package com.iktakademija.serialization.project.controllers.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktakademija.serialization.project.entities.AccountEntity;
import com.iktakademija.serialization.project.entities.AddressEntity;
import com.iktakademija.serialization.project.entities.UserEmploymentEntity;
import com.iktakademija.serialization.project.security.Views;

@JsonRootName("User report")
@JsonPropertyOrder({ "name", "workRole", "salary", "address", "city", "noOfAccounts" })
public class DatabaseReportDTO {

	@JsonView(Views.CEO.class)
	@JsonProperty("Users name")
	private String name;
	
	@JsonView(Views.CEO.class)
	@JsonProperty("Date of birth")
	private LocalDate dateOfBirth;
	
	@JsonView(Views.CEO.class)
	@JsonProperty("Email")
	private String email;
	
	@JsonView(Views.Admin.class)
	@JsonProperty("Password")
	private String password;
	
	@JsonView(Views.CEO.class)
	@JsonProperty("Address information")
	private AddressEntity address;
	
	@JsonView(Views.Admin.class)
	@JsonProperty("Accounts information")
	private List<AccountEntity> accounts = new ArrayList<>();
	
	@JsonView(Views.CEO.class)
	@JsonProperty("Employment information")
	private UserEmploymentEntity employment;

	public DatabaseReportDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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

	public AddressEntity getAddress() {
		return address;
	}

	public void setAddress(AddressEntity address) {
		this.address = address;
	}

	public List<AccountEntity> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountEntity> accounts) {
		this.accounts = accounts;
	}

	public UserEmploymentEntity getEmployment() {
		return employment;
	}

	public void setEmployment(UserEmploymentEntity employment) {
		this.employment = employment;
	}

}
