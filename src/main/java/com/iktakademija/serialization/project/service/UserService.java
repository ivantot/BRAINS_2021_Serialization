package com.iktakademija.serialization.project.service;

import com.iktakademija.serialization.project.entities.UserEntity;

public interface UserService {

	public UserEntity updateUser(Integer id, UserEntity user);
}
