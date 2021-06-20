package com.iktakademija.serialization.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktakademija.serialization.project.controllers.dto.UserEmploymentAssignmentDTO;
import com.iktakademija.serialization.project.controllers.util.RESTError;
import com.iktakademija.serialization.project.entities.UserEmploymentEntity;
import com.iktakademija.serialization.project.repositories.UserEmploymentRepository;
import com.iktakademija.serialization.project.security.Views;
import com.iktakademija.serialization.project.service.UserEmploymentService;

@RestController
@RequestMapping(path = "/api/v1/employment")
public class UserEmploymentController {

	@Autowired
	private UserEmploymentRepository userEmploymentRepository;

	@Autowired
	private UserEmploymentService userEmploymentService;

	//CRUD - create
	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.POST, value = "/{userID}/private")
	public ResponseEntity<?> createEmployment(@PathVariable Integer userID, @RequestBody UserEmploymentAssignmentDTO employment) {
		//check for valid user input
		if (userID == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found, please check the input!"),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserEmploymentEntity>(userEmploymentService.createEmployment(employment, userID),
				HttpStatus.CREATED);
	}

	//CRUD - read
	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{employmentID}")
	public ResponseEntity<?> findEmploymentByID(@PathVariable Integer employmentID) {
		//
		if (employmentID == null) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Bad request"), HttpStatus.BAD_REQUEST);
		}
		if (!userEmploymentRepository.findById(employmentID).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Employment not found"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserEmploymentEntity>(userEmploymentRepository.findById(employmentID).get(),
				HttpStatus.OK);
	}

	@JsonView(Views.CEO.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> findAllEmployments() {
		List<UserEmploymentEntity> employments = (List<UserEmploymentEntity>) userEmploymentRepository.findAll();

		if (employments.size() == 0) {
			// return 404
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			// return 200
			return new ResponseEntity<List<UserEmploymentEntity>>(employments, HttpStatus.OK);
		}
	}

	//CRUD - update
	@JsonView(Views.CEO.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{employmentID}")
	public ResponseEntity<?> updateEmployment(@PathVariable Integer employmentID,
			@RequestBody UserEmploymentEntity employment) {
		//check for valid employment input
		if (employmentID == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Employment not found, please check the input!"),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserEmploymentEntity>(
				userEmploymentService.updateEmployment(employmentID, employment), HttpStatus.OK);
	}

	//CRUD - delete
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{employmentID}")
	public ResponseEntity<?> removeAccountbyID(@PathVariable Integer employmentID) {
		//check for valid employment input
		if (userEmploymentRepository.findById(employmentID).isEmpty()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Employment not found, please check the input!"),
					HttpStatus.BAD_REQUEST);
		}
		UserEmploymentEntity employmentForDeletion = userEmploymentRepository.findById(employmentID).get();
		userEmploymentRepository.delete(employmentForDeletion);
		return new ResponseEntity<UserEmploymentEntity>(employmentForDeletion, HttpStatus.I_AM_A_TEAPOT);
	}
}
