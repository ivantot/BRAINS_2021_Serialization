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
import com.iktakademija.serialization.project.controllers.dto.UserRegisterDTO;
import com.iktakademija.serialization.project.controllers.util.RESTError;
import com.iktakademija.serialization.project.entities.UserEntity;
import com.iktakademija.serialization.project.repositories.UserRepository;
import com.iktakademija.serialization.project.security.Views;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.GET, path = "/public")
	@JsonView(Views.Public.class)

	public ResponseEntity<?> getAllUsersPublic() {
		List<UserEntity> users = (List<UserEntity>) userRepository.findAll();

		if (users.size() == 0) {
			// return 404
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			// return 200
			return new ResponseEntity<List<UserEntity>>(users, HttpStatus.OK);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/private")
	@JsonView(Views.Private.class)
	public ResponseEntity<?> getAllUsersPrivate() {
		List<UserEntity> users = (List<UserEntity>) userRepository.findAll();

		if (users.size() == 0) {
			// return 404
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			// return 200
			return new ResponseEntity<List<UserEntity>>(users, HttpStatus.OK);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/admin")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getAllUsersAdmin() {
		List<UserEntity> users = (List<UserEntity>) userRepository.findAll();

		if (users.size() == 0) {
			// return 404
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			// return 200
			return new ResponseEntity<List<UserEntity>>(users, HttpStatus.OK);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{userID}")
	@JsonView(Views.Private.class)
	public ResponseEntity<?> getUserById(@PathVariable Integer userID) {
		//
		if (userID == null) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Bad request"), HttpStatus.BAD_REQUEST);
		}
		if (!userRepository.findById(userID).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserEntity>(userRepository.findById(userID).get(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	@JsonView(Views.Private.class)
	public ResponseEntity<?> saveUser(@RequestBody UserRegisterDTO userRegisterDTO) {
		//if name/email/DOB/password null return bad request
		if (userRegisterDTO.getName() == null || userRegisterDTO.getEmail() == null
				|| userRegisterDTO.getDateOfBirth() == null || userRegisterDTO.getPassword() == null) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Bad request"), HttpStatus.BAD_REQUEST);
		}
		// if email existing in database dont allow to create user
		if (userRepository.findByEmail(userRegisterDTO.getEmail()).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Email exists in database"), HttpStatus.BAD_REQUEST);
		}
		//store user to database
		UserEntity userEntity = new UserEntity();
		userEntity.setName(userRegisterDTO.getName());
		userEntity.setEmail(userRegisterDTO.getEmail());
		userEntity.setDateOfBirth(userRegisterDTO.getDateOfBirth());
		userEntity.setPassword(userRegisterDTO.getPassword());
		
		return new ResponseEntity<UserEntity>(userRepository.save(userEntity), HttpStatus.CREATED);
	}
	/*
		
		//update
		@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
		public ResponseEntity<?> updateUser(@PathVariable Integer userID, @RequestBody UserEntity user) {
		return null;
		}
		
		//delete
		@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
		public ResponseEntity<?> removeUserbyID(@PathVariable Integer userID) {
		return null;
		}
		*/
}
