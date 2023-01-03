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
		accountRepository.postData(data);
	}

	public List<AccountData> getAllData() throws Exception {
		return accountRepository.getAllData();
	}

	public AccountData getDataById(String id) throws Exception {
		return accountRepository.getDataById(id);
	}

	public void updateDataById(AccountData data, String id) throws Exception {
		accountRepository.updateDataById(data, id);
	}

	public void deteteData(String id) throws Exception {
		accountRepository.deteteData(id);
	}

	public void deleteAllData() throws Exception {
		accountRepository.deleteAllData();
	}
}
