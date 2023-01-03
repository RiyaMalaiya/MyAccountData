package com.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.model.AccountData;

@Repository
public class AccountRepository {

	@Autowired
	private DynamoDBMapper dynamoDBMapper;

	public void postData(AccountData data) throws Exception {

		AccountData datafromDB = dynamoDBMapper.load(AccountData.class, data.getProfileId());
		if (datafromDB == null) {
			dynamoDBMapper.save(data);
		} else {
			throw new Exception("profileId exist");
		}

	}

	public List<AccountData> getAllData() throws Exception {
		List<AccountData> data = dynamoDBMapper.scan(AccountData.class, new DynamoDBScanExpression());
		if (data == null || data.isEmpty()) {
			throw new Exception("Data does not exist");
		}
		return data;
	}

	public AccountData getDataById(String id) throws Exception {
		AccountData data = dynamoDBMapper.load(AccountData.class, id);
		if (data == null) {
			throw new Exception("Data does not exist");
		}
		return data;
	}

	public void updateDataById(AccountData data, String id) throws Exception {

		AccountData datafromDB = dynamoDBMapper.load(AccountData.class, id);
		if (datafromDB != null) {
			dynamoDBMapper.save(data, new DynamoDBSaveExpression().withExpectedEntry("profileId",
					new ExpectedAttributeValue(new AttributeValue().withS(id))));
		} else {
			throw new Exception("profileId not exist");
		}

	}

	public void deteteData(String id) throws Exception {

		AccountData datafromDB = dynamoDBMapper.load(AccountData.class, id);
		if (datafromDB != null) {
			dynamoDBMapper.delete(datafromDB);
		} else {
			throw new Exception("profileId not exist");
		}
	}

	public void deleteAllData() throws Exception {
		List<AccountData> data = dynamoDBMapper.scan(AccountData.class, new DynamoDBScanExpression());
		if (data != null && !data.isEmpty()) {
			List<String> idList = data.stream().map(id -> id.getProfileId()).collect(Collectors.toList());
			for (String id : idList) {
				dynamoDBMapper.delete(dynamoDBMapper.load(AccountData.class, id));
			}
		} else {
			throw new Exception("Data does not exist");
		}
	}
}
