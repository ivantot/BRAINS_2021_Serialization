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
import com.iktakademija.serialization.project.controllers.dto.AddressRegistrationDTO;
import com.iktakademija.serialization.project.controllers.util.RESTError;
import com.iktakademija.serialization.project.entities.AddressEntity;
import com.iktakademija.serialization.project.repositories.AddressRepository;
import com.iktakademija.serialization.project.security.Views;
import com.iktakademija.serialization.project.service.AddressService;

@RestController
@RequestMapping(path = "api/v1/addresses")
public class AddressController {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private AddressService addressService;

	//create
	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createAddress(@RequestBody AddressEntity address) {
		//check for valid user input
		if (address.getStreet() == null || address.getCity() == null || address.getCountry() == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Address not valid, please check the input!"),
					HttpStatus.BAD_REQUEST);
		}
		AddressRegistrationDTO addressDTO = addressService.convertEntityToDTO(addressService.createAddress(address));
		return new ResponseEntity<AddressRegistrationDTO>(addressDTO, HttpStatus.CREATED);
	}

	//read
	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{addressID}")
	public ResponseEntity<?> findAddressbyID(@PathVariable Integer addressID) {
		//
		if (addressID == null) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Bad request"), HttpStatus.BAD_REQUEST);
		}
		if (!addressRepository.findById(addressID).isPresent()) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Address not found"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<AddressEntity>(addressRepository.findById(addressID).get(), HttpStatus.OK);
	}

	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> findAllAddress() {
		List<AddressEntity> addresses = (List<AddressEntity>) addressRepository.findAll();

		if (addresses.size() == 0) {
			// return 404
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			// return 200
			return new ResponseEntity<List<AddressEntity>>(addresses, HttpStatus.OK);
		}
	}

	//update
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{addressID}")
	public ResponseEntity<?> updateAddress(@PathVariable Integer addressID, @RequestBody AddressEntity address) {
		//check for valid user input
		if (addressID == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Address not found, please check the input!"),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<AddressEntity>(addressService.updateAddress(addressID, address), HttpStatus.OK);
	}

	//delete
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> removeAddressbyID(@PathVariable Integer addressID) {
		//check for valid address input
		if (addressID == null) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Address not found, please check the input!"),
					HttpStatus.BAD_REQUEST);
		}
		AddressEntity addressForDeletion = addressRepository.findById(addressID).get();
		addressRepository.delete(addressForDeletion);
		return new ResponseEntity<AddressEntity>(addressForDeletion, HttpStatus.I_AM_A_TEAPOT);
	}
}
