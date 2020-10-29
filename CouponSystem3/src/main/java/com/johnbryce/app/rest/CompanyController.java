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
import com.johnbryce.app.beans.Company;
import com.johnbryce.app.beans.Coupon;
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
@RequestMapping("company")
@NoArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@Getter
public class CompanyController extends ClientController {

	@Autowired
	private LoginManager loginManager;

	private String token;

	@Autowired
	private TokenManager tokenManager;

	@PostMapping("login/{email}/{password}")
	public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) throws LoginException {
		token = loginManager.login2(email, password, ClientType.Company);
		if (token != null) {
			LoginResponse loginResponse = new LoginResponse();
			loginResponse.setToken(token);
			loginResponse.setType(ClientType.Company);
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

	@PutMapping("updateCompany")
	public ResponseEntity<?> updateCompany(@RequestBody Company company) {
		try {
			companyService.updateCompany(company);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (NotExistException | NotAllowedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("setCompanyId/{id}")
	public ResponseEntity<?> setCompanyId(@PathVariable int id) {
		this.companyService.setCompanyID(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping("addCoupon")
	public ResponseEntity<?> addCoupon(@RequestBody Coupon coupon) {
		try {
			companyService.addCoupon(coupon);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (NotAllowedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("updateCoupon")
	public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon) {
		try {
			companyService.updateCoupon(coupon);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("deleteCoupon/{id}")
	public ResponseEntity<?> deleteCoupon(@PathVariable int id) {
		try {
			companyService.deleteCoupon(id);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("getOneCoupon/{id}")
	public ResponseEntity<?> getOneCoupon(@PathVariable int id) {
		try {
			return new ResponseEntity<Coupon>(companyService.getOneCoupon(id), HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("getCompanyCoupons/{id}")
	public ResponseEntity<?> getCompanyCoupons(@PathVariable int id) {
		companyService.setCompanyID(id);
		return new ResponseEntity<List<Coupon>>(companyService.getCompanyCoupons(), HttpStatus.OK);
	}

	@GetMapping("getCompanyCouponsCategory/{categoryID}/{id}")
	public ResponseEntity<?> getCompanyCoupons(@PathVariable Category categoryID, @PathVariable int id) {
		try {
			companyService.setCompanyID(id);
			return new ResponseEntity<List<Coupon>>(companyService.getCompanyCoupons(categoryID), HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("getCompanyCouponsMaxPrice/{price}/{id}")
	public ResponseEntity<?> getCompanyCoupons(@PathVariable double price, @PathVariable int id) {
		companyService.setCompanyID(id);
		return new ResponseEntity<List<Coupon>>(companyService.getCompanyCoupons(price), HttpStatus.OK);
	}

	@GetMapping("getCompanyDetails/{id}")
	public ResponseEntity<?> getCompanyDetails(@PathVariable int id) {
		companyService.setCompanyID(id);
		return new ResponseEntity<Company>(companyService.getCompanyDetailes(), HttpStatus.OK);
	}

	@GetMapping("getCompanyByEmailAndPassword/{email}/{password}")
	public ResponseEntity<?> getCompanyByEmailAndPassword(@PathVariable String email, @PathVariable String password) {
		return new ResponseEntity<Company>(companyService.getCompanyByEmailAndPassword(email, password), HttpStatus.OK);
	}

	@GetMapping("getCompanyIdByEmailAndPassword/{email}/{password}")
	public ResponseEntity<?> getCompanyIdByEmailAndPassword(@PathVariable String email, @PathVariable String password) {
		return new ResponseEntity<Integer>(companyService.getCompanyIdByEmailAndPassword(email, password),
				HttpStatus.OK);
	}
}
