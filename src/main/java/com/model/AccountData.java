package com.model;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Data;

@Data
@DynamoDBTable(tableName = "AccountData")
public class AccountData {

	@DynamoDBHashKey
	String profileId;

	@DynamoDBAttribute
	List<AccountDetails> accountDetails;

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public List<AccountDetails> getAccountDetails() {
		return accountDetails;
	}

	public void setAccountDetails(List<AccountDetails> accountDetails) {
		this.accountDetails = accountDetails;
	}

}
