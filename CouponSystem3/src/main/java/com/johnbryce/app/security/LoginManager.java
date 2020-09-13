package com.johnbryce.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.johnbryce.app.exceptions.LoginException;
import com.johnbryce.app.exceptions.NotAllowedException;
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

	public String login2(String email, String password, ClientType clientType) throws LoginException {
		switch (clientType) {
		case Administrator:
			if (adminService.login(email, password)) {
				return tokenManager.addToken(adminService);
			}
		case Company:
			if (companyService.login(email, password)) {
				int companyID = companyService.getCompanyIdByEmailAndPassword(email, password);
				companyService.setCompanyID(companyID);
				return tokenManager.addToken(companyService);
			}
		case Customer:
			if (customerService.login(email, password)) {
				int customerID = customerService.getCustomerIdByEmailAndPassword(email, password);
				customerService.setCustomerID(customerID);
				return tokenManager.addToken(customerService);
			}

		default:
			throw new LoginException("Invalid user or password or type");
		}
	}

	public ClientService login(String email, String password, ClientType clientType) throws NotAllowedException {
		switch (clientType) {
		case Administrator:
			if (adminService.login(email, password) == true) {
				return adminService;
			}
		case Customer:
			if (customerService.login(email, password) == true) {
				return customerService;
			}
		case Company:
			if (companyService.login(email, password) == true) {
				return companyService;
			}
		default:
			throw new NotAllowedException("email or password or type wrongs");
		}
	}
}
