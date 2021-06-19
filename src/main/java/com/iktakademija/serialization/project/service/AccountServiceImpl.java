package com.iktakademija.serialization.project.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktakademija.serialization.project.entities.AccountEntity;
import com.iktakademija.serialization.project.repositories.AccountRepository;
import com.iktakademija.serialization.project.repositories.UserRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AccountRepository accountRepository;

	//utility method for generating random account numbers
	public String generateRandomAcoountNumber() {
		Random rand = new Random();
		String card = "SRB";
		for (int i = 0; i < 14; i++) {
			int n = rand.nextInt(10) + 0;
			card += Integer.toString(n);
		}
		return card;
	}

	/**
	 * makes a new account and assigns to user, saves account and user in db
	 * @return AccountEntity
	 * @param Integer userID
	 * 
	 */
	@Override
	public AccountEntity createAccount(Integer userID) {

		//check for valid input and user in db
		if (userRepository.findById(userID).isPresent()) {
			// create user
			AccountEntity account = new AccountEntity();
			account.setAccountNo(generateRandomAcoountNumber());
			account.setBalance(0.00);
			account.setUser(userRepository.findById(userID).get());
			userRepository.save(userRepository.findById(userID).get());
			return accountRepository.save(account);
		}
		return null;
	}

	@Override
	public AccountEntity updateAccount(Integer accountID, AccountEntity account) {
		// check for existance in db
		Optional<AccountEntity> accountFromDB = accountRepository.findById(accountID);

		if (accountFromDB.isPresent()) {
			// update aaccount where parameters are valid
			if (account.getAccountNo() != null) {
				accountFromDB.get().setAccountNo(account.getAccountNo());
			}
			if (account.getBalance() != null) {
				accountFromDB.get().setBalance(account.getBalance());
			}
			if (account.getUser() != null) {
				accountFromDB.get().setUser(account.getUser());
			}

			//save updated account to db
			accountRepository.save(accountFromDB.get());
		}
		return null;
	}

}
