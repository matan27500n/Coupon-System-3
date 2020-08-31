package com.johnbryce.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public ResponseEntity<Boolean> login(@PathVariable String email, @PathVariable String password) {
		return new ResponseEntity<Boolean>(companyService.login(email, password), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addCoupon")
	public ResponseEntity<Void> addCoupon(@RequestBody Coupon coupon) throws NotAllowedException {
		companyService.addCoupon(coupon);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/updateCoupon")
	public ResponseEntity<Void> updateCoupon(@RequestBody Coupon coupon) throws NotExistException {
		companyService.updateCoupon(coupon);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteCoupon")
	public ResponseEntity<Void> deleteCoupon(@PathVariable int couponID) throws NotExistException {
		companyService.deleteCoupon(couponID);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getCompanyCoupons")
	public ResponseEntity<List<Coupon>> getCompanyCoupons() {
		return new ResponseEntity<List<Coupon>>(companyService.getAllCompanyCoupons(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getCompanyCouponsCategory")
	public ResponseEntity<List<Coupon>> getCompanyCoupons(@PathVariable Category category) {
		return new ResponseEntity<List<Coupon>>(companyService.getCompanyCoupons(category), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getCompanyCouponsMax")
	public ResponseEntity<List<Coupon>> getCompanyCoupons(@PathVariable double maxPrice) {
		return new ResponseEntity<List<Coupon>>(companyService.getCompanyCoupons(maxPrice), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getCompanyDetails")
	public ResponseEntity<Company> getCompanyDetails() {
		return new ResponseEntity<Company>(companyService.getCompanyDetailes(), HttpStatus.OK);
	}

}
