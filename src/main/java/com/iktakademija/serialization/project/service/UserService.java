package com.iktakademija.serialization.project.service;

import java.util.List;

import com.iktakademija.serialization.project.controllers.dto.DatabaseReportDTO;
import com.iktakademija.serialization.project.controllers.dto.UserReportDTO;
import com.iktakademija.serialization.project.entities.UserEntity;

public interface UserService {

	public UserEntity updateUser(Integer id, UserEntity user);
	
	public List<UserReportDTO> findByCompany(String company);
	
	public DatabaseReportDTO findByUser(String name);
}
