package com.iktakademija.serialization.project.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktakademija.serialization.project.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
	
	Optional<UserEntity> findByEmail(String email);
	
	Optional<UserEntity> findByName(String name);

}
