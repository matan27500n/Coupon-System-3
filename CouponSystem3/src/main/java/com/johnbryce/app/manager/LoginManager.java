package com.johnbryce.app.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johnbryce.app.service.AdminService;
import com.johnbryce.app.service.ClientService;
import com.johnbryce.app.service.CompanyService;
import com.johnbryce.app.service.CustomerService;

@Service
public class LoginManager {

	@Autowired
	private AdminService adminService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	TokenManager tokenManager;

	private ClientService clientService;
}
