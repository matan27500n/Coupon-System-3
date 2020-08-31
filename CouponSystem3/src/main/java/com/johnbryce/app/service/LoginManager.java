package com.johnbryce.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.johnbryce.app.exceptions.NotAllowedException;

@Service
public class LoginManager {

	@Autowired
	AdminService adminService;

	@Autowired
	CustomerService customerService;

	@Autowired
	CompanyService companyService;

	public ClientService login(String email, String password, ClientType clientType) throws NotAllowedException {
		switch (clientType) {
		case Administrator:
			if (adminService.login(email, password) == true) {

				return adminService;
			}
			adminService.printWrongMessage();
			return null;
		case Customer:
			if (customerService.login(email, password) == true) {
				return customerService;
			}
			customerService.printWrongMessage();
			return null;
		case Company:
			if (companyService.login(email, password) == true) {
				return companyService;
			}
			adminService.printWrongMessage();
			return null;
		default:
			System.out.println("The Client type is not from the list,login failed...");
			return null;
		}
	}
}
