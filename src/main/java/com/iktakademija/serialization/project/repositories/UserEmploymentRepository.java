package com.iktakademija.serialization.project.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.iktakademija.serialization.project.entities.UserEmploymentEntity;

public interface UserEmploymentRepository extends CrudRepository<UserEmploymentEntity, Integer> {
	
	List<UserEmploymentEntity> findAllByCompany(String company);

}
