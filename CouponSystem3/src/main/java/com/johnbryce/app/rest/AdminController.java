package com.johnbryce.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.johnbryce.app.beans.Company;
import com.johnbryce.app.beans.Customer;
import com.johnbryce.app.exceptions.AlreadyExitsException;
import com.johnbryce.app.exceptions.LoginException;
import com.johnbryce.app.exceptions.NotAllowedException;
import com.johnbryce.app.exceptions.NotExistException;
import com.johnbryce.app.manager.LoginManager;
import com.johnbryce.app.service.AdminService;
import com.johnbryce.app.service.ClientType;

import lombok.NoArgsConstructor;

@RestController
@RequestMapping("admin")
@NoArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private LoginManager loginManager;

	@PostMapping("login")
	public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
		HttpHeaders responseHeaders = new HttpHeaders();
		try {
			String token = loginManager.login2(email, password, ClientType.Administrator);
			responseHeaders.set("Coupon System Header", token);
			return ResponseEntity.ok().headers(responseHeaders).body("You are loged in as admin..");
		} catch (LoginException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
		// return new ResponseEntity<Boolean>(adminService.login(email, password),
		// HttpStatus.OK);
	}

	@PostMapping("addCompany")
	public ResponseEntity<Void> addCompany(@RequestBody Company company) throws AlreadyExitsException {
		adminService.addCompany(company);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/update")
	public ResponseEntity<Void> updateCompany(@RequestBody Company company)
			throws NotExistException, NotAllowedException {
		adminService.updateCompany(company);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/delete")
	public ResponseEntity<Void> deleteCompany(@PathVariable int companyID) throws NotExistException {
		adminService.deleteCompany(companyID);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllCompanies")
	public ResponseEntity<List<Company>> getAllCompanies() {
		return new ResponseEntity<List<Company>>(adminService.getAllCompanies(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getOneCompany")
	public ResponseEntity<Company> getOneCompany(@PathVariable int companyID) {
		return new ResponseEntity<Company>(adminService.getOneCompany(companyID), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addCustomer")
	public ResponseEntity<Void> addCustomer(@RequestBody Customer customer) throws AlreadyExitsException {
		adminService.addCustomer(customer);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/updateCustomer")
	public ResponseEntity<Void> updateCustomer(@RequestBody Customer customer) throws NotAllowedException {
		adminService.updateCustomer(customer);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteCustomer")
	public ResponseEntity<Void> deleteCustomer(@RequestBody Customer customer) throws NotExistException {
		adminService.deleteCustomer(customer);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllCustomers")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return new ResponseEntity<List<Customer>>(adminService.getAllCustomers(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getOneCustomer")
	public ResponseEntity<Customer> getOneCustomer(int customerID) {
		return new ResponseEntity<Customer>(adminService.getOneCustomer(customerID), HttpStatus.OK);
	}

}
