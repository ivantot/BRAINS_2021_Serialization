package com.iktakademija.serialization.project.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktakademija.serialization.project.entities.AddressEntity;

@RestController
@RequestMapping(path = "api/v1/addresses")
public class AddressController {

	//create
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createAddress(@RequestBody AddressEntity address) {
		return null;
	}
	
	//read
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findAddressbyID(@PathVariable Integer addressID) {
		return null;
	}
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> findAllAddress() {
		return null;
	}
	
	//update
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateAddress(@PathVariable Integer addressID, @RequestBody AddressEntity address) {
		return null;
	}
	
	//delete
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> removeAddressbyID(@PathVariable Integer addressID) {
		return null;
	}
}
