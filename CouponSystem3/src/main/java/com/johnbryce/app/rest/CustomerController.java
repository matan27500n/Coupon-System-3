package com.johnbryce.app.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("customer")
@NoArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class CustomerController extends ClientController {

	@Autowired
	private LoginManager loginManager;

	@Autowired
	private TokenManager tokenManager;

	@PostMapping("login/{email}/{password}")
	public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) throws LoginException {
		String token = loginManager.login2(email, password, ClientType.Customer);
		if (token != null) {
			LoginResponse loginResponse = new LoginResponse();
			loginResponse.setToken(token);
			loginResponse.setType(ClientType.Customer);
			return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.CREATED);
		}
		return new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * @PostMapping("purchaseCoupon") public ResponseEntity<?>
	 * purchaseCoupon(@RequestBody Coupon coupon, @RequestHeader String token) { try
	 * { tokenManager.isTokenExist(token); customerService.purchaseCoupon(coupon);
	 * return new ResponseEntity<Void>(HttpStatus.OK); } catch (NotExistException e)
	 * { return ResponseEntity.badRequest().body(e.getMessage()); } catch
	 * (NotAllowedException e) { return
	 * ResponseEntity.badRequest().body(e.getMessage()); } }
	 */

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

	/*
	 * @GetMapping("customerCoupons") public ResponseEntity<?>
	 * getCustomerCoupons(@RequestHeader String token) { try {
	 * tokenManager.isTokenExist(token); return new
	 * ResponseEntity<List<Coupon>>(customerService.getCustomerCoupons(),
	 * HttpStatus.OK); } catch (NotExistException e) { return
	 * ResponseEntity.badRequest().body(e.getMessage()); } }
	 */

	@GetMapping("customerCoupons/{id}")
	public ResponseEntity<?> getCustomerCoupons(@PathVariable int id) {
		customerService.setCustomerID(id);
		return new ResponseEntity<List<Coupon>>(customerService.getCustomerCoupons(), HttpStatus.OK);
	}

	@GetMapping("customerCouponsCategory")
	public ResponseEntity<?> getCustomerCoupons(@PathVariable Category category, @RequestHeader String token) {
		try {
			tokenManager.isTokenExist(token);
			return new ResponseEntity<List<Coupon>>(customerService.getCustomerCoupons(category), HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("customerCouponsMaxPriceS")
	public ResponseEntity<?> getCustomerCoupons(@PathVariable double maxPrice, @RequestHeader String token) {
		try {
			tokenManager.isTokenExist(token);
			return new ResponseEntity<List<Coupon>>(customerService.getCustomerCoupons(maxPrice), HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("customerDetails")
	public ResponseEntity<?> getCustomerDetails(@RequestHeader String token) {
		try {
			tokenManager.isTokenExist(token);
			return new ResponseEntity<Customer>(customerService.getCustomerDetailes(), HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("getCustomerByEmailAndPassword/{email}/{password}")
	public ResponseEntity<?> getCustomerByEmailAndPassword(@PathVariable String email, @PathVariable String password) {
		return new ResponseEntity<Customer>(customerService.getCustomerByEmailAndPassword(email, password),
				HttpStatus.OK);
	}
}
