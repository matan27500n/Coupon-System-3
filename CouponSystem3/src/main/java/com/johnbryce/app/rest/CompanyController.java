package com.johnbryce.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.johnbryce.app.beans.Category;
import com.johnbryce.app.beans.Company;
import com.johnbryce.app.beans.Coupon;
import com.johnbryce.app.exceptions.NotAllowedException;
import com.johnbryce.app.exceptions.NotExistException;
import com.johnbryce.app.service.CompanyService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("company")
@NoArgsConstructor
@AllArgsConstructor
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@PostMapping("loging")
	public ResponseEntity<Boolean> login(@PathVariable String email, @PathVariable String password) {
		return new ResponseEntity<Boolean>(companyService.login(email, password), HttpStatus.OK);
	}

	@PostMapping("addCoupon")
	public ResponseEntity<Void> addCoupon(@RequestBody Coupon coupon) throws NotAllowedException {
		companyService.addCoupon(coupon);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@PutMapping("updateCoupon")
	public ResponseEntity<Void> updateCoupon(@RequestBody Coupon coupon) throws NotExistException {
		companyService.updateCoupon(coupon);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@DeleteMapping("deleteCoupon")
	public ResponseEntity<Void> deleteCoupon(@PathVariable int couponID) throws NotExistException {
		companyService.deleteCoupon(couponID);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("getCompanyCoupons")
	public ResponseEntity<List<Coupon>> getCompanyCoupons() {
		return new ResponseEntity<List<Coupon>>(companyService.getAllCompanyCoupons(), HttpStatus.OK);
	}

	@GetMapping("getCompanyCouponsCategory")
	public ResponseEntity<List<Coupon>> getCompanyCoupons(@PathVariable Category category) {
		return new ResponseEntity<List<Coupon>>(companyService.getCompanyCoupons(category), HttpStatus.OK);
	}

	@GetMapping("getCompanyCouponsMaxPrice")
	public ResponseEntity<List<Coupon>> getCompanyCoupons(@PathVariable double maxPrice) {
		return new ResponseEntity<List<Coupon>>(companyService.getCompanyCoupons(maxPrice), HttpStatus.OK);
	}

	@GetMapping("getCompanyDetails")
	public ResponseEntity<Company> getCompanyDetails() {
		return new ResponseEntity<Company>(companyService.getCompanyDetailes(), HttpStatus.OK);
	}

}
