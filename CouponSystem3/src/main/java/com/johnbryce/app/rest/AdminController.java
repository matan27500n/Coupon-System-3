package com.johnbryce.app.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import com.johnbryce.app.beans.Company;
import com.johnbryce.app.beans.Coupon;
import com.johnbryce.app.beans.Customer;
import com.johnbryce.app.beans.LoginResponse;
import com.johnbryce.app.exceptions.AlreadyExitsException;
import com.johnbryce.app.exceptions.LoginException;
import com.johnbryce.app.exceptions.NotAllowedException;
import com.johnbryce.app.exceptions.NotExistException;
import com.johnbryce.app.security.ClientType;
import com.johnbryce.app.security.LoginManager;
import com.johnbryce.app.security.TokenManager;

import lombok.Getter;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("admin")
@NoArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@Getter
public class AdminController extends ClientController {

	private String token;

	@Autowired
	private LoginManager loginManager;

	@Autowired
	private TokenManager tokenManager;

	@PostMapping("login/{email}/{password}")
	public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
		try {
			token = loginManager.login2(email, password, ClientType.Administrator);
			LoginResponse loginResponse = new LoginResponse();
			loginResponse.setToken(token);
			loginResponse.setType(ClientType.Administrator);
			return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.CREATED);

		} catch (LoginException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("logout")
	public ResponseEntity<?> logout(@RequestHeader(name = "Coupon-System-Header") String token) {
		tokenManager.removeToken(token);
		System.out.println("Remove token successfully");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("addCompany")
	public ResponseEntity<?> addCompany(@RequestBody Company company,

			@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			adminService.addCompany(company);
			return new ResponseEntity<Company>(HttpStatus.CREATED);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (AlreadyExitsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("updateCompany")
	public ResponseEntity<?> updateCompany(@RequestBody Company company,
			@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			adminService.updateCompany(company);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NotAllowedException enew) {
			return ResponseEntity.badRequest().body(enew.getMessage());
		}
	}

	@DeleteMapping("deleteCompany/{id}")
	public ResponseEntity<?> deleteCompany(@PathVariable int id,
			@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			adminService.deleteCompany(id);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("GetAllCompanies")
	public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Coupon-System-Header") String token)
			throws NotAllowedException {
		try {
			tokenManager.isTokenExist(token);
		} catch (NotExistException e) {
			return new ResponseEntity<>("you are not allowed, token is not exists", HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<List<Company>>(adminService.getAllCompanies(), HttpStatus.OK);

	}

	@GetMapping("getOneCompany/{id}")
	public ResponseEntity<?> getOneCompany(@PathVariable int id,
			@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			return new ResponseEntity<Company>(adminService.getOneCompany(id), HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("addCustomer")
	public ResponseEntity<?> addCustomer(@RequestBody Customer customer,
			@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			adminService.addCustomer(customer);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (AlreadyExitsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("updateCustomer")
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer,
			@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			adminService.updateCustomer(customer);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NotAllowedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("deleteCustomer/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable int id,
			@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			adminService.deleteCustomer(id);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("getAllCustomers")
	public ResponseEntity<?> getAllCustomers(@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			return new ResponseEntity<List<Customer>>(adminService.getAllCustomers(), HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("getOneCustomer/{id}")
	public ResponseEntity<?> getOneCustomer(@PathVariable int id,
			@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			return new ResponseEntity<Customer>(adminService.getOneCustomer(id), HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("getAllCoupons")
	public ResponseEntity<?> getAllCoupons(@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			return new ResponseEntity<List<Coupon>>(adminService.getAllCoupons(), HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("getOneCoupon/{id}")
	public ResponseEntity<?> getOneCoupon(@PathVariable int id,
			@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			return new ResponseEntity<Coupon>(adminService.getOneCoupon(id), HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
