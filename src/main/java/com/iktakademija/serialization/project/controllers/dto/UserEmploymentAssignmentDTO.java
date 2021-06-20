package com.iktakademija.serialization.project.controllers.dto;

import com.iktakademija.serialization.project.controllers.util.EWorkRole;

public class UserEmploymentAssignmentDTO {

	private String company;
	private EWorkRole workRole;

	public UserEmploymentAssignmentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public EWorkRole getWorkRole() {
		return workRole;
	}

	public void setWorkRole(EWorkRole workRole) {
		this.workRole = workRole;
	}

}
