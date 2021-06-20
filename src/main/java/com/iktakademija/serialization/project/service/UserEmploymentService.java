package com.iktakademija.serialization.project.service;

import com.iktakademija.serialization.project.controllers.dto.UserEmploymentAssignmentDTO;
import com.iktakademija.serialization.project.entities.UserEmploymentEntity;

public interface UserEmploymentService {

	public UserEmploymentEntity createEmployment(UserEmploymentAssignmentDTO employment, Integer userID);
	public UserEmploymentEntity updateEmployment(Integer id, UserEmploymentEntity employment);
	public UserEmploymentAssignmentDTO convertEntityToDTO(UserEmploymentEntity employment);
	
}
