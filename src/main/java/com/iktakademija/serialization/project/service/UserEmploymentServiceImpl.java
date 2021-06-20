package com.iktakademija.serialization.project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktakademija.serialization.project.controllers.dto.UserEmploymentAssignmentDTO;
import com.iktakademija.serialization.project.controllers.util.EWorkRole;
import com.iktakademija.serialization.project.entities.UserEmploymentEntity;
import com.iktakademija.serialization.project.entities.UserEntity;
import com.iktakademija.serialization.project.repositories.UserEmploymentRepository;
import com.iktakademija.serialization.project.repositories.UserRepository;

@Service
public class UserEmploymentServiceImpl implements UserEmploymentService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserEmploymentRepository userEmploymentRepository;

	@Override
	public UserEmploymentEntity createEmployment(UserEmploymentAssignmentDTO employment, Integer userID) {
		// only allow creating employment for existing users
		if (userRepository.findById(userID).isPresent()) {
			// prepare employment entity and populate
			UserEmploymentEntity newEmployment = new UserEmploymentEntity();
			if (employment.getCompany() != null) {
				newEmployment.setCompany(employment.getCompany());
			}
			if (employment.getWorkRole() != null) {
				newEmployment.setWorkRole(employment.getWorkRole());
			}
			// set minimum salary
			if(newEmployment.getWorkRole().equals(EWorkRole.CEO)) {
				newEmployment.setSalary(150000.00);
			}
			if(newEmployment.getWorkRole().equals(EWorkRole.MANAGER)) {
				newEmployment.setSalary(100000.00);
			}
			if(newEmployment.getWorkRole().equals(EWorkRole.STAFF)) {
				newEmployment.setSalary(30000.00);
			}
			userEmploymentRepository.save(newEmployment);
			//set user employment
			UserEntity user = userRepository.findById(userID).get();
			user.setEmployment(newEmployment);
			userRepository.save(user);
			return newEmployment;
		}
		return null;
	}

	@Override
	public UserEmploymentEntity updateEmployment(Integer employmentID, UserEmploymentEntity employment) {
		// check for existance in db
		Optional<UserEmploymentEntity> employmentFromDB = userEmploymentRepository.findById(employmentID);

		if (employmentFromDB.isPresent()) {
			// update employment where parameters are valid
			if (employment.getCompany() != null) {
				employmentFromDB.get().setCompany(employment.getCompany());
			}
			if (employment.getWorkRole() != null) {
				employmentFromDB.get().setWorkRole(employment.getWorkRole());
			}
			if (employment.getUsers() != null) {
				employmentFromDB.get().setUsers(employment.getUsers());
			}
			if (employment.getSalary() != null) {
				employmentFromDB.get().setSalary(employment.getSalary());
			}
			//save updated employment to db
			userEmploymentRepository.save(employmentFromDB.get());
		}
		return null;
	}

	@Override
	public UserEmploymentAssignmentDTO convertEntityToDTO(UserEmploymentEntity employment) {
		// TODO Auto-generated method stub
		return null;
	}

}
