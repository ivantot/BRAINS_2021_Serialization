package com.iktakademija.serialization.project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktakademija.serialization.project.entities.AddressEntity;

public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {

}
