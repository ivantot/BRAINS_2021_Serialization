package com.iktakademija.serialization.project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktakademija.serialization.project.entities.UserEntity;
import com.iktakademija.serialization.project.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

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
}
