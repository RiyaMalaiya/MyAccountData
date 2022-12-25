package com.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.model.AccountData;

@Repository
public class AccountRepository {

	@Autowired
	private DynamoDBMapper dynamoDBMapper;

	public void postData(AccountData data) {
		dynamoDBMapper.save(data);
	}

	public List<AccountData> getAllData() {
		List<AccountData> data = dynamoDBMapper.scan(AccountData.class, new DynamoDBScanExpression());
		return data;
	}

	public AccountData getDataById(String id) {
		return dynamoDBMapper.load(AccountData.class, id);
	}
}
