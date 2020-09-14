package com.johnbryce.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.johnbryce.app.exceptions.LoginException;
import com.johnbryce.app.service.AdminService;
import com.johnbryce.app.service.CompanyService;
import com.johnbryce.app.service.CustomerService;

public abstract class ClientController {

	@Autowired
	protected AdminService adminService;

	@Autowired
	protected CompanyService companyService;

	@Autowired
	protected CustomerService customerService;

	public abstract ResponseEntity<?> login(String email, String password) throws LoginException;

}
