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
import com.iktakademija.serialization.project.controllers.dto.DatabaseReportDTO;
import com.iktakademija.serialization.project.controllers.dto.UserRegisterDTO;
import com.iktakademija.serialization.project.controllers.dto.UserReportDTO;
import com.iktakademija.serialization.project.controllers.util.RESTError;
import com.iktakademija.serialization.project.entities.UserEntity;
import com.iktakademija.serialization.project.repositories.UserEmploymentRepository;
import com.iktakademija.serialization.project.repositories.UserRepository;
import com.iktakademija.serialization.project.security.Views;
import com.iktakademija.serialization.project.service.UserService;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private UserEmploymentRepository userEmploymentRepository;

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

	//update
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> updateUser(@PathVariable Integer userID, @RequestBody UserEntity user) {
		//check for valid user input
		if (userID == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found, please check the input!"),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserEntity>(userService.updateUser(userID, user), HttpStatus.OK);
	}

	//delete
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> removeUserbyID(@PathVariable Integer userID) {
		//check for valid user input
		if (userID == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "User not found, please check the input!"),
					HttpStatus.BAD_REQUEST);
		}
		UserEntity userForDeletion = userRepository.findById(userID).get();
		userRepository.delete(userForDeletion);
		return new ResponseEntity<UserEntity>(userForDeletion, HttpStatus.I_AM_A_TEAPOT);
	}

	/**
	 * controller returns specialized report for CEO on users that work within a single company 
	 * 
	 * @param takes a company name (String)
	 * @return ResponseEntity with various http responses depending on result of logic
	 */
	// 3.1
	@JsonView(Views.CEO.class)
	@RequestMapping(method = RequestMethod.GET, value = "reports/CEO/{company}")
	public ResponseEntity<?> getUserByCompanyCEO(@PathVariable String company) {

		// check if company exists in db
		if (userEmploymentRepository.findAllByCompany(company).isEmpty()) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Company not found in database."),
					HttpStatus.NOT_FOUND);
		}
		// return company report if all checks pass
		return new ResponseEntity<List<UserReportDTO>>(userService.findByCompany(company), HttpStatus.OK);
	}

	// 3.2
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "reports/Admin/{company}")
	public ResponseEntity<?> getUserByCompanyAdmin(@PathVariable String company) {

		// check if company exists in db
		if (userEmploymentRepository.findAllByCompany(company).isEmpty()) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Company not found in database."),
					HttpStatus.NOT_FOUND);
		}
		// return company report if all checks pass
		return new ResponseEntity<List<UserReportDTO>>(userService.findByCompany(company), HttpStatus.OK);
	}

	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "reports/Private/{company}")
	public ResponseEntity<?> getUserByCompanyPrivate(@PathVariable String company) {

		// check if company exists in db
		if (userEmploymentRepository.findAllByCompany(company).isEmpty()) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Company not found in database."),
					HttpStatus.NOT_FOUND);
		}
		// return company report if all checks pass
		return new ResponseEntity<List<UserReportDTO>>(userService.findByCompany(company), HttpStatus.OK);
	}

	@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "reports/Public/{company}")
	public ResponseEntity<?> getUserByCompanyPublic(@PathVariable String company) {

		// check if company exists in db
		if (userEmploymentRepository.findAllByCompany(company).isEmpty()) {
			return new ResponseEntity<RESTError>(new RESTError(3, "Company not found in database."),
					HttpStatus.NOT_FOUND);
		}
		// return company report if all checks pass
		return new ResponseEntity<List<UserReportDTO>>(userService.findByCompany(company), HttpStatus.OK);
	}

	// 3.3
	@JsonView(Views.CEO.class)
	@RequestMapping(method = RequestMethod.GET, value = "userReports/CEO/{name}")
	public ResponseEntity<?> getUserReportCEO(@PathVariable String name) {

		// check if name exists in db
		if (userRepository.findByName(name).isEmpty()) {
			return new ResponseEntity<RESTError>(new RESTError(123, "User not found in database."),
					HttpStatus.NOT_FOUND);
		}
		// return report if all checks pass
		return new ResponseEntity<DatabaseReportDTO>(userService.findByUser(name), HttpStatus.OK);
	}

	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "userReports/Admin/{name}")
	public ResponseEntity<?> getUserReportAdmin(@PathVariable String name) {

		// check if name exists in db
		if (userRepository.findByName(name).isEmpty()) {
			return new ResponseEntity<RESTError>(new RESTError(123, "User not found in database."),
					HttpStatus.NOT_FOUND);
		}
		// return report if all checks pass
		return new ResponseEntity<DatabaseReportDTO>(userService.findByUser(name), HttpStatus.OK);
	}

}
