package com.iktakademija.serialization.project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktakademija.serialization.project.controllers.util.EWorkRole;
import com.iktakademija.serialization.project.security.Views;

@Entity
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
@Table(name = "Employment")
public class UserEmploymentEntity {

	@Id
	@GeneratedValue
	@JsonView(Views.Private.class)
	private Integer id;

	@JsonView(Views.Private.class)
	@Column(nullable = false)
	private String company;

	@JsonView(Views.Private.class)
	@Column(nullable = false)
	private EWorkRole workRole;

	@JsonView(Views.CEO.class)
	@Column(nullable = false)
	private Double salary;
	
	@Version
	private Integer version;
	
	@JsonBackReference("3")
	@OneToMany(mappedBy = "employment", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<UserEntity> users = new ArrayList<>();

	
	public UserEmploymentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}
	
	

}
