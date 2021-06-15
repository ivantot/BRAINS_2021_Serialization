package com.iktakademija.serialization.project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktakademija.serialization.project.entities.AccountEntity;

public interface AccountRepository extends CrudRepository<AccountEntity, Integer> {

}
