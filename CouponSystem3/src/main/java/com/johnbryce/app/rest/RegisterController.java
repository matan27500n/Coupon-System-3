package com.johnbryce.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.johnbryce.app.beans.Company;
import com.johnbryce.app.beans.Customer;
import com.johnbryce.app.service.AdminService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class RegisterController {

	@Autowired
	private AdminService adminService;

	@PostMapping("registerCompany")
	public ResponseEntity<?> registerCompany(@RequestBody Company company) {
		adminService.registerCompany(company);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@PostMapping("registerCustomer")
	public ResponseEntity<?> registerCustomer(@RequestBody Customer customer) {
		adminService.registerCustomer(customer);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
