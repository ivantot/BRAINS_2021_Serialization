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
import com.iktakademija.serialization.project.controllers.util.RESTError;
import com.iktakademija.serialization.project.entities.AccountEntity;
import com.iktakademija.serialization.project.repositories.AccountRepository;
import com.iktakademija.serialization.project.security.Views;
import com.iktakademija.serialization.project.service.AccountService;

@RestController
@RequestMapping(path = "api/v1/accounts")
public class AccountController {

	@Autowired
	AccountService accountService;

	@Autowired
	private AccountRepository accountRepository;

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
	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{accountID}")
	public ResponseEntity<?> findAccountbyID(@PathVariable Integer accountID) {
		//
		if (accountID == null) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Bad request"), HttpStatus.BAD_REQUEST);
		}
		if (!accountRepository.findById(accountID).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Account not found"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<AccountEntity>(accountRepository.findById(accountID).get(), HttpStatus.OK);
	}
	
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> findAllAccounts() {
		List<AccountEntity> accounts = (List<AccountEntity>) accountRepository.findAll();

		if (accounts.size() == 0) {
			// return 404
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			// return 200
			return new ResponseEntity<List<AccountEntity>>(accounts, HttpStatus.OK);
		}
	}

	//CRUD - update
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{accountID}")
	public ResponseEntity<?> updateAccount(@PathVariable Integer accountID, @RequestBody AccountEntity account) {
		//check for valid user input
		if (accountID == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Account not found, please check the input!"),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<AccountEntity>(accountService.updateAccount(accountID, account), HttpStatus.OK);
	}

	//CRUD - delete
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> removeAccountbyID(@PathVariable Integer accountID) {
		//check for valid address input
		if (accountID == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Account not found, please check the input!"),
					HttpStatus.BAD_REQUEST);
		}
		AccountEntity accountForDeletion = accountRepository.findById(accountID).get();
		accountRepository.delete(accountForDeletion);
		return new ResponseEntity<AccountEntity>(accountForDeletion, HttpStatus.I_AM_A_TEAPOT);
	}
}
