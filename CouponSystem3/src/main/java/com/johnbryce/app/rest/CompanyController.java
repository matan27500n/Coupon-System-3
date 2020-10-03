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
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("company")
@NoArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class CompanyController extends ClientController {

	private final long milliseconds = 1800000;

	@Autowired
	private LoginManager loginManager;

	@Autowired
	private TokenManager tokenManager;

	@PostMapping("login/{email}/{password}")
	public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) throws LoginException {
		String token = loginManager.login2(email, password, ClientType.Company);
		if (token != null) {
			LoginResponse loginResponse = new LoginResponse();
			loginResponse.setToken(token);
			loginResponse.setType(ClientType.Company);
			return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.CREATED);
		}
		return new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("addCoupon")
	public ResponseEntity<?> addCoupon(@RequestBody Coupon coupon,
			@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			if (System.currentTimeMillis() >= tokenManager.getTimestamp(token) + milliseconds) {
				tokenManager.deleteOldTokens(token);
				throw new NotAllowedException("the token is not available any more");
			}
			companyService.addCoupon(coupon);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (NotAllowedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("updateCoupon")
	public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon,
			@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			if (System.currentTimeMillis() >= tokenManager.getTimestamp(token) + milliseconds) {
				tokenManager.deleteOldTokens(token);
				throw new NotAllowedException("the token is not available any more");
			}
			companyService.updateCoupon(coupon);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NotAllowedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("deleteCoupon/{id}")
	public ResponseEntity<?> deleteCoupon(@PathVariable int id,
			@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			if (System.currentTimeMillis() >= tokenManager.getTimestamp(token) + milliseconds) {
				tokenManager.deleteOldTokens(token);
				throw new NotAllowedException("the token is not available any more");
			}
			companyService.deleteCoupon(id);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NotAllowedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("getCompanyCoupons/{id}")
	public ResponseEntity<?> getCompanyCoupons(@PathVariable int id,
			@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			if (System.currentTimeMillis() >= tokenManager.getTimestamp(token) + milliseconds) {
				tokenManager.deleteOldTokens(token);
				throw new NotAllowedException("the token is not available any more");
			}
			companyService.setCompanyID(id);
			return new ResponseEntity<List<Coupon>>(companyService.getCompanyCoupons(), HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NotAllowedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("getCompanyCouponsCategory/{categoryID}")
	public ResponseEntity<?> getCompanyCoupons(@PathVariable Category categoryID,
			@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			if (System.currentTimeMillis() >= tokenManager.getTimestamp(token) + milliseconds) {
				tokenManager.deleteOldTokens(token);
				throw new NotAllowedException("the token is not available any more");
			}
			return new ResponseEntity<List<Coupon>>(companyService.getCompanyCoupons(categoryID), HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NotAllowedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("getCompanyCouponsMaxPrice/{price}")
	public ResponseEntity<?> getCompanyCoupons(@PathVariable double price,
			@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			if (System.currentTimeMillis() >= tokenManager.getTimestamp(token) + milliseconds) {
				tokenManager.deleteOldTokens(token);
				throw new NotAllowedException("the token is not available any more");
			}
			return new ResponseEntity<List<Coupon>>(companyService.getCompanyCoupons(price), HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NotAllowedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("getCompanyDetails/{id}")
	public ResponseEntity<?> getCompanyDetails(@PathVariable int id,
			@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			if (System.currentTimeMillis() >= tokenManager.getTimestamp(token) + milliseconds) {
				tokenManager.deleteOldTokens(token);
				throw new NotAllowedException("the token is not available any more");
			}
			companyService.setCompanyID(id);
			return new ResponseEntity<Company>(companyService.getCompanyDetailes(), HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NotAllowedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("getCompanyByEmailAndPassword/{email}/{password}")
	public ResponseEntity<?> getCompanyByEmailAndPassword(@PathVariable String email, @PathVariable String password,
			@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			if (System.currentTimeMillis() >= tokenManager.getTimestamp(token) + milliseconds) {
				tokenManager.deleteOldTokens(token);
				throw new NotAllowedException("the token is not available any more");
			}
			return new ResponseEntity<Company>(companyService.getCompanyByEmailAndPassword(email, password),
					HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NotAllowedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("getCompanyIdByEmailAndPassword/{email}/{password}")
	public ResponseEntity<?> getCompanyIdByEmailAndPassword(@PathVariable String email, @PathVariable String password,
			@RequestHeader(name = "Coupon-System-Header") String token) {
		try {
			tokenManager.isTokenExist(token);
			if (System.currentTimeMillis() >= tokenManager.getTimestamp(token) + milliseconds) {
				tokenManager.deleteOldTokens(token);
				throw new NotAllowedException("the token is not available any more");
			}
			return new ResponseEntity<Integer>(companyService.getCompanyIdByEmailAndPassword(email, password),
					HttpStatus.OK);
		} catch (NotExistException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NotAllowedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
