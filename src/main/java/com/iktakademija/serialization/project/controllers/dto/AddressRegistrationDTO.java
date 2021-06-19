package com.iktakademija.serialization.project.controllers.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktakademija.serialization.project.security.Views;

public class AddressRegistrationDTO {

	@JsonView(Views.Public.class)
	private String street;

	@JsonView(Views.Public.class)
	private String city;

	@JsonView(Views.Public.class)
	private String country;

	public AddressRegistrationDTO() {
		super();
		// TODO Auto-generated constructor stub

	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
