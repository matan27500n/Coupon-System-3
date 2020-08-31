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
import com.johnbryce.app.beans.Coupon;
import com.johnbryce.app.beans.Customer;
import com.johnbryce.app.exceptions.NotAllowedException;
import com.johnbryce.app.exceptions.NotExistException;
import com.johnbryce.app.service.CustomerService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("customer")
@NoArgsConstructor
@AllArgsConstructor
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public ResponseEntity<Boolean> login(@PathVariable String email, @PathVariable String password) {
		return new ResponseEntity<Boolean>(customerService.login(email, password), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/purchase")
	public ResponseEntity<Void> purchaseCoupon(@RequestBody Coupon coupon)
			throws NotExistException, NotAllowedException {
		customerService.purchaseCoupon(coupon);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/CustomerCoupons")
	public ResponseEntity<List<Coupon>> getCustomerCoupons() {
		return new ResponseEntity<List<Coupon>>(customerService.getCustomerCoupons(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/CustomerCouponsCategory")
	public ResponseEntity<List<Coupon>> getCustomerCoupons(@PathVariable Category category) {
		return new ResponseEntity<List<Coupon>>(customerService.getCustomerCoupons(category), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/CustomerCouponsMax")
	public ResponseEntity<List<Coupon>> getCustomerCoupons(@PathVariable double maxPrice) {
		return new ResponseEntity<List<Coupon>>(customerService.getCustomerCoupons(maxPrice), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/CustomerDetails")
	public ResponseEntity<Customer> getCustomerDetails() {
		return new ResponseEntity<Customer>(customerService.getCustomerDetailes(), HttpStatus.OK);
	}

}
