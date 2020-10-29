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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.johnbryce.app.beans.Category;
import com.johnbryce.app.beans.Coupon;
import com.johnbryce.app.beans.Customer;
import com.johnbryce.app.beans.LoginResponse;
import com.johnbryce.app.exceptions.LoginException;
import com.johnbryce.app.exceptions.NotAllowedException;
import com.johnbryce.app.exceptions.NotExistException;
import com.johnbryce.app.security.ClientType;
import com.johnbryce.app.security.LoginManager;
import com.johnbryce.app.security.TokenManager;

import lombok.Getter;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("customer")
@NoArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@Getter
public class CustomerController extends ClientController {

	private String token;

	@Autowired
	private LoginManager loginManager;

	@Autowired
	private TokenManager tokenManager;

	@PostMapping("login/{email}/{password}")
	public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) throws LoginException {
		token = loginManager.login2(email, password, ClientType.Customer);
		if (token != null) {
			LoginResponse loginResponse = new LoginResponse();
			loginResponse.setToken(token);
			loginResponse.setType(ClientType.Customer);
			return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.CREATED);
		}
		return new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("logout")
	public ResponseEntity<?> logout() {
		tokenManager.removeToken(token);
		System.out.println("Remove token successfully");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("updateCustomer")
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
		try {
			adminService.updateCustomer(customer);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (NotAllowedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("updateCustomerID/{id}")
	public ResponseEntity<?> setCustomerId(@PathVariable int id) {
		this.customerService.setCustomerID(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping("purchaseCoupon")
	public ResponseEntity<?> purchaseCoupon(@RequestBody Coupon coupon) {
		try {
			customerService.purchaseCoupon(coupon);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (NotExistException | NotAllowedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("deleteCoupon/{id}")
	public ResponseEntity<?> deleteCouponPurchase(@PathVariable int id) {
		try {
			customerService.deleteCouponPurchase(id);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("customerCoupons/{id}")
	public ResponseEntity<?> getCustomerCoupons(@PathVariable int id) {
		customerService.setCustomerID(id);
		return new ResponseEntity<List<Coupon>>(customerService.getCustomerCoupons(), HttpStatus.OK);
	}

	@GetMapping("customerCouponsCategory/{category}/{id}")
	public ResponseEntity<?> getCustomerCoupons(@PathVariable Category category, @PathVariable int id) {
		customerService.setCustomerID(id);
		return new ResponseEntity<List<Coupon>>(customerService.getCustomerCoupons(category), HttpStatus.OK);
	}

	@GetMapping("customerCouponsMaxPrice/{price}/{id}")
	public ResponseEntity<?> getCustomerCoupons(@PathVariable double price, @PathVariable int id) {
		customerService.setCustomerID(id);
		return new ResponseEntity<List<Coupon>>(customerService.getCustomerCoupons(price), HttpStatus.OK);
	}

	@GetMapping("customerDetails")
	public ResponseEntity<?> getCustomerDetails() {
		return new ResponseEntity<Customer>(customerService.getCustomerDetailes(), HttpStatus.OK);
	}

	@GetMapping("getCustomerByEmailAndPassword/{email}/{password}")
	public ResponseEntity<?> getCustomerByEmailAndPassword(@PathVariable String email, @PathVariable String password) {
		return new ResponseEntity<Customer>(customerService.getCustomerByEmailAndPassword(email, password),
				HttpStatus.OK);
	}
}
