package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.model.AccountData;
import com.repository.AccountRepository;

@Component
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public void postData(AccountData data) throws Exception {
			accountRepository.postData(data);
	}

	public List<AccountData> getAllData() {
		return accountRepository.getAllData();
	}

	public AccountData getDataById(String id) {
		return accountRepository.getDataById(id);
	}
	
	public void updateDataById(AccountData data, String id) throws Exception {
		accountRepository.updateDataById(data, id);
	}
	
	public void deteteData(String id) throws Exception {
		accountRepository.deteteData(id);
	}
}
