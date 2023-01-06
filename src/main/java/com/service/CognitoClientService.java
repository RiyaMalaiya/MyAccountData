package com.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AdminRespondToAuthChallengeRequest;
import com.amazonaws.services.cognitoidp.model.AdminRespondToAuthChallengeResult;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.ChallengeNameType;
import com.amazonaws.services.cognitoidp.model.InvalidParameterException;
import com.model.Request;
import com.model.Response;

//sending data to the cognito
@Component
public class CognitoClientService {

	private final String CLIENT_ID = "77rd2jrfu1iiriqdlpr5e3ksan";
	private final String USER_POOL = "us-east-1_BKO6NFwhC";

	@Value("${aws.dynamodb.accesskey}")
	String accessKey;

	@Value("${aws.dynamodb.secretkey}")
	String secretKey;

	public Response processLogin(Request userLoginRequestPayload) throws Exception {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
		AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(Regions.US_EAST_1).build();
		Response userLoginResponsePayload = new Response();
		final Map<String, String> authParams = new HashMap<>();
		authParams.put("USERNAME", userLoginRequestPayload.getUserName());
		authParams.put("PASSWORD", userLoginRequestPayload.getPassword());
		final AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest();
		authRequest.withAuthFlow(AuthFlowType.ADMIN_USER_PASSWORD_AUTH).withClientId(CLIENT_ID)
				.withUserPoolId(USER_POOL).withAuthParameters(authParams);
		try {
			AdminInitiateAuthResult result = cognitoClient.adminInitiateAuth(authRequest);
			AuthenticationResultType authenticationResult = null;
			if (result.getChallengeName() != null && !result.getChallengeName().isEmpty()) {
				System.out.println("Challenge Name is " + result.getChallengeName());
				if (result.getChallengeName().contentEquals("NEW_PASSWORD_REQUIRED")) {
					if (userLoginRequestPayload.getPassword() == null) {
						throw new Exception("User must change password " + result.getChallengeName());
					} else {
						final Map<String, String> challengeResponses = new HashMap<>();
						challengeResponses.put("USERNAME", userLoginRequestPayload.getUserName());
						challengeResponses.put("PASSWORD", userLoginRequestPayload.getPassword());
						// add new password
						challengeResponses.put("NEW_PASSWORD", userLoginRequestPayload.getNewPassword());
						final AdminRespondToAuthChallengeRequest request = new AdminRespondToAuthChallengeRequest()
								.withChallengeName(ChallengeNameType.NEW_PASSWORD_REQUIRED)
								.withChallengeResponses(challengeResponses).withClientId(CLIENT_ID)
								.withUserPoolId(USER_POOL).withSession(result.getSession());
						AdminRespondToAuthChallengeResult resultChallenge = cognitoClient
								.adminRespondToAuthChallenge(request);
						authenticationResult = resultChallenge.getAuthenticationResult();
						userLoginResponsePayload.setAccessToken(authenticationResult.getAccessToken());
						userLoginResponsePayload.setRefreshToken(authenticationResult.getRefreshToken());
					}
				} else {
					throw new Exception("User has other challenge " + result.getChallengeName());
				}
				cognitoClient.shutdown();
				return userLoginResponsePayload;
			} else {
				System.out.println("User has no challenge");
				authenticationResult = result.getAuthenticationResult();
				userLoginResponsePayload.setAccessToken(authenticationResult.getAccessToken());
				userLoginResponsePayload.setRefreshToken(authenticationResult.getRefreshToken());
				cognitoClient.shutdown();
				return userLoginResponsePayload;
			}
		} catch (InvalidParameterException e) {
			cognitoClient.shutdown();
			throw new Exception(e.getErrorMessage());
		} catch (Exception e) {
			cognitoClient.shutdown();
			throw new Exception(e.getMessage());
		}
	}
}
