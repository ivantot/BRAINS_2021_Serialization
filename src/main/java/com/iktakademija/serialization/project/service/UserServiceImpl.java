package com.iktakademija.serialization.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktakademija.serialization.project.controllers.dto.DatabaseReportDTO;
import com.iktakademija.serialization.project.controllers.dto.UserReportDTO;
import com.iktakademija.serialization.project.entities.UserEntity;
import com.iktakademija.serialization.project.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@PersistenceContext
	private EntityManager em;

	/**
	 * custom method for updating user in database
	 * @return UserEntity
	 * @param Integer userID, UserEntity
	 */
	@Override
	public UserEntity updateUser(Integer userID, UserEntity user) {

		// check for existance in db
		Optional<UserEntity> userFromDB = userRepository.findById(userID);
		if (userFromDB.isPresent()) {
			// update user where parameters are valid
			if (user.getName() != null) {
				userFromDB.get().setName(user.getName());
			}
			if (user.getEmail() != null) {
				userFromDB.get().setEmail(user.getEmail());
			}
			if (user.getDateOfBirth() != null) {
				userFromDB.get().setDateOfBirth(user.getDateOfBirth());
			}
			if (user.getPassword() != null) {
				userFromDB.get().setPassword(user.getPassword());
			}
			if (user.getAddress() != null) {
				userFromDB.get().setAddress(user.getAddress());
			}
			//save updated user to db
			userRepository.save(userFromDB.get());
		}
		return null;
	}

	@Override
	public List<UserReportDTO> findByCompany(String company) {

		// write a query to find users based on company
		//		String sql = "SELECT u FROM UserEntity u LEFT JOIN FETCH u.employment e WHERE e.company = :company";

		String sql = "FROM UserEntity u WHERE u.employment.company = :company";

		// invoke the sql statement
		Query query = em.createQuery(sql);
		query.setParameter("company", company);

		// convert entity to dto
		List<UserEntity> users = query.getResultList();
		List<UserReportDTO> retVal = new ArrayList<>();
		for (UserEntity user : users) {
			UserReportDTO userDTO = new UserReportDTO();
			if (user.getName() != null) {
				userDTO.setName(user.getName());
			}
			if (user.getAddress() != null) {
				if (user.getAddress().getStreet() != null) {
					userDTO.setAddress(user.getAddress().getStreet());
				}
				if (user.getAddress().getCity() != null) {
					userDTO.setCity(user.getAddress().getCity());
				}
			}
			if (user.getEmployment().getWorkRole() != null) {
				userDTO.setWorkRole(user.getEmployment().getWorkRole());
			}
			if (user.getEmployment().getSalary() != null) {
				userDTO.setSalary(user.getEmployment().getSalary());
			}
			if (user.getAccounts() != null) {
				userDTO.setNoOfAccounts(user.getAccounts().size());
			}
			retVal.add(userDTO);
		}

		// handle the return value of the sql statement

		return retVal;
	}

	@Override
	public DatabaseReportDTO findByUser(String name) {

		Optional<UserEntity> user = userRepository.findByName(name);
		DatabaseReportDTO userReport = new DatabaseReportDTO();

		if (user.isPresent()) {

			if (!user.get().getAccounts().isEmpty()) {
				userReport.setAccounts(user.get().getAccounts());
			}

			if (user.get().getAddress() != null) {
				userReport.setAddress(user.get().getAddress());
			}

			if (user.get().getEmployment() != null) {
				userReport.setEmployment(user.get().getEmployment());
			}

			userReport.setDateOfBirth(user.get().getDateOfBirth());
			userReport.setEmail(user.get().getEmail());
			userReport.setName(user.get().getName());
			userReport.setPassword(user.get().getPassword());

		}

		return userReport;
	}
}
