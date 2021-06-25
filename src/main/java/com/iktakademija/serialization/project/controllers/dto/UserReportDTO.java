package com.iktakademija.serialization.project.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktakademija.serialization.project.controllers.util.EWorkRole;
import com.iktakademija.serialization.project.security.Views;

@JsonRootName("Users")
@JsonPropertyOrder({ "name", "workRole", "salary", "address", "city", "noOfAccounts" })
public class UserReportDTO {

	@JsonView(Views.Public.class)
	@JsonProperty("Users name")
	private String name;
	@JsonView(Views.CEO.class)
	@JsonProperty("Users address")
	private String address;
	@JsonView(Views.Private.class)
	@JsonProperty("Users city")
	private String city;
	@JsonView(Views.Private.class)
	@JsonProperty("Users role in company")
	private EWorkRole workRole;
	@JsonProperty("Users salary")
	@JsonView(Views.CEO.class)
	private Double salary;
	@JsonView(Views.Admin.class)
	@JsonProperty("Number of accounts within bank")
	private Integer noOfAccounts;

	public UserReportDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public EWorkRole getWorkRole() {
		return workRole;
	}

	public void setWorkRole(EWorkRole workRole) {
		this.workRole = workRole;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Integer getNoOfAccounts() {
		return noOfAccounts;
	}

	public void setNoOfAccounts(Integer noOfAccounts) {
		this.noOfAccounts = noOfAccounts;
	}

}
