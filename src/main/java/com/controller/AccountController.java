package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.AccountData;
import com.service.AccountService;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping("/saveData/{id}")
	public ResponseEntity<String> postData(@RequestBody AccountData data, @PathVariable("id") String id) {
		data.setProfileId(id);
		try {
			accountService.postData(data);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Succefully created Entity");
	}

	@GetMapping("/getAllData")
	public ResponseEntity<List<AccountData>> getAllData() {
		List<AccountData> data = new ArrayList<AccountData>();
		try {
			data = accountService.getAllData();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	@GetMapping("/getAllData/{id}")
	public ResponseEntity<AccountData> getDataById(@PathVariable("id") String id) {
		AccountData data = new AccountData();
		try {
		data = accountService.getDataById(id);
	} catch (Exception e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	@PutMapping("/updateData/{id}")
	public ResponseEntity<String> updateDataById(@RequestBody AccountData data, @PathVariable(value = "id") String id) {
		data.setProfileId(id);
		try {
			accountService.updateDataById(data, id);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Succefully updated Entity");
	}

	@DeleteMapping("/deleteData/{id}")
	public ResponseEntity<String> deteteData(@PathVariable(value = "id") String id) {
		try {
			accountService.deteteData(id);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Succefully deleted data for : " + id);
	}

	@DeleteMapping("/deleteAllData")
	public ResponseEntity<String> deleteAllData() throws Exception {
		try {
			accountService.deleteAllData();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Succefully deleted Entity");
	}
}
