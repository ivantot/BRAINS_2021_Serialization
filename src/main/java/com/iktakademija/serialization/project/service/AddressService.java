package com.iktakademija.serialization.project.service;

import com.iktakademija.serialization.project.controllers.dto.AddressRegistrationDTO;
import com.iktakademija.serialization.project.entities.AddressEntity;

public interface AddressService {

	public AddressEntity createAddress(AddressEntity address);
	public AddressEntity updateAddress(Integer id, AddressEntity address);
	public AddressRegistrationDTO convertEntityToDTO(AddressEntity address);


}
