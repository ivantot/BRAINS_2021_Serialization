package com.iktakademija.serialization.project.service;

import com.iktakademija.serialization.project.entities.AccountEntity;

public interface AccountService {

	public AccountEntity createAccount(Integer userID);

	public AccountEntity updateAccount(Integer accountID, AccountEntity account);

}
