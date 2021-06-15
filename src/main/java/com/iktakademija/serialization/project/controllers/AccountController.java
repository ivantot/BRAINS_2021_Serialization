package com.iktakademija.serialization.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktakademija.serialization.project.controllers.util.RESTError;
import com.iktakademija.serialization.project.entities.AccountEntity;
import com.iktakademija.serialization.project.security.Views;
import com.iktakademija.serialization.project.service.AccountService;

@RestController
@RequestMapping(path = "api/v1/accounts")
public class AccountController {

	@Autowired
	AccountService accountService;

	//CRUD - create
	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.POST, value = "/{userID}/private")
	public ResponseEntity<?> createAccount(@PathVariable Integer userID) {
		//check for valid user input
		if (userID == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found, please check the input!"),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<AccountEntity>(accountService.createAccount(userID), HttpStatus.CREATED);
	}

	//CRUD - read
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findAccountbyID(@PathVariable Integer accountID) {
		return null;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> findAllAccounts() {
		return null;
	}

	//CRUD - update
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateAccount(@PathVariable Integer accountID, @RequestBody AccountEntity account) {
		return null;
	}

	//CRUD - delete
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> removeAccountbyID(@PathVariable Integer accountID) {
		return null;
	}
}
