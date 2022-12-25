package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.model.AccountData;
import com.repository.AccountRepository;

@Component
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public void postData(AccountData data) throws Exception {

		AccountData datafromDB = accountRepository.getDataById(data.getProfileId());
		if (datafromDB == null) {
			accountRepository.postData(data);
		} else {
			throw new Exception("profileId exist");
		}

	}

	public List<AccountData> getAllData() {
		return accountRepository.getAllData();
	}

	public AccountData getDataById(String id) {
		return accountRepository.getDataById(id);
	}
}
