package com.johnbryce.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.johnbryce.app.beans.Company;
import com.johnbryce.app.beans.Customer;
import com.johnbryce.app.beans.LoginResponse;
import com.johnbryce.app.exceptions.AlreadyExitsException;
import com.johnbryce.app.exceptions.LoginException;
import com.johnbryce.app.exceptions.NotAllowedException;
import com.johnbryce.app.exceptions.NotExistException;
import com.johnbryce.app.security.ClientType;
import com.johnbryce.app.security.LoginManager;
import com.johnbryce.app.security.TokenManager;
import com.johnbryce.app.service.AdminService;

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

	@Autowired
	private TokenManager tokenManager;

	/*
	 * @PostMapping("login") public ResponseEntity<?> login(@PathVariable String
	 * email, @PathVariable String password) { HttpHeaders responseHeaders = new
	 * HttpHeaders(); try { String token = loginManager.login2(email, password,
	 * ClientType.Administrator); responseHeaders.set("Coupon System Header",
	 * token); return ResponseEntity.ok().headers(responseHeaders).
	 * body("You are loged in as admin.."); } catch (LoginException e) { return new
	 * ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED); } }
	 */

	@PostMapping("login")
	public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) throws LoginException {
		String token = loginManager.login2(email, password, ClientType.Administrator);
		if (token != null) {
			LoginResponse loginResponse = new LoginResponse();
			loginResponse.setToken(token);
			loginResponse.setType(ClientType.Administrator);
			return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.CREATED);
		}
		return new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("addCompany")
	public ResponseEntity<?> addCompany(@RequestBody Company company,
			@RequestHeader(name = "Coupon-System-Header") String token) throws AlreadyExitsException {
		try {
			tokenManager.isTokenExist(token);
			adminService.addCompany(company);
			return new ResponseEntity<Company>(HttpStatus.CREATED);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("updateCompany")
	public ResponseEntity<Void> updateCompany(@RequestBody Company company)
			throws NotExistException, NotAllowedException {
		adminService.updateCompany(company);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@DeleteMapping("deleteCompany")
	public ResponseEntity<Void> deleteCompany(@PathVariable int companyID) throws NotExistException {
		adminService.deleteCompany(companyID);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("GetAllCompanies")
	public ResponseEntity<List<Company>> getAllCompanies() {
		return new ResponseEntity<List<Company>>(adminService.getAllCompanies(), HttpStatus.OK);
	}

	@GetMapping("getOneCompany")
	public ResponseEntity<Company> getOneCompany(@PathVariable int companyID) throws NotExistException {
		return new ResponseEntity<Company>(adminService.getOneCompany(companyID), HttpStatus.OK);
	}

	@PostMapping("addCustomer")
	public ResponseEntity<Void> addCustomer(@RequestBody Customer customer) throws AlreadyExitsException {
		adminService.addCustomer(customer);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@PutMapping("updateCustomer")
	public ResponseEntity<Void> updateCustomer(@RequestBody Customer customer) throws NotAllowedException {
		adminService.updateCustomer(customer);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@DeleteMapping("deleteCustomer")
	public ResponseEntity<Void> deleteCustomer(@RequestBody Customer customer) throws NotExistException {
		adminService.deleteCustomer(customer);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("getAllCustomers")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return new ResponseEntity<List<Customer>>(adminService.getAllCustomers(), HttpStatus.OK);
	}

	@GetMapping("getOneCustomer")
	public ResponseEntity<Customer> getOneCustomer(int customerID) throws NotExistException {
		return new ResponseEntity<Customer>(adminService.getOneCustomer(customerID), HttpStatus.OK);
	}

}
