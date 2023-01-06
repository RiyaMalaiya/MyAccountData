package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.Request;
import com.model.Response;
import com.service.CognitoClientService;

@RestController
public class CognitoController {

	@Autowired
	private CognitoClientService cognitoClientService;

	@PostMapping(path = "/login")
	public ResponseEntity<Object> login(@RequestBody Request userLoginRequestPayload) throws Exception {
		try {
			Response response = cognitoClientService.processLogin(userLoginRequestPayload);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
