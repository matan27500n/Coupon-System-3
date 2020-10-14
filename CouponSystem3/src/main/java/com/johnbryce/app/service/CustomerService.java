package com.johnbryce.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.johnbryce.app.beans.Coupon;
import com.johnbryce.app.beans.Customer;
import com.johnbryce.app.exceptions.NotAllowedException;
import com.johnbryce.app.exceptions.NotExistException;
import lombok.Getter;
import lombok.Setter;
import com.johnbryce.app.beans.Category;

@Service
@Scope("prototype")
@Setter
@Getter
public class CustomerService extends ClientService {

	private int customerID;

	@Override
	public boolean login(String email, String password) {
		if (customerRepository.findByEmailAndPassword(email, password) == null) {
			return false;
		}
		return true;
	}

	public void updateCustomer(Customer customer) throws NotAllowedException {
		if (customerRepository.findById(customer.getId()) == null) {
			throw new NotAllowedException("The id doesn't exists in the system");
		}
		customerRepository.saveAndFlush(customer);
	}

	public void purchaseCoupon(Coupon coupon) throws NotExistException, NotAllowedException {
		if (couponRepository.existsById(coupon.getId()) == false) {
			throw new NotExistException("The couponID doesn't exists in the system");
		}
		System.out.println("the customer id: " + customerID);
		Customer customer = customerRepository.getOne(customerID);
		List<Coupon> customerCoupons = customer.getCoupons();
		for (Coupon coupon2 : customerCoupons) {
			if (coupon2.getId() == coupon.getId()) {
				throw new NotAllowedException("You are not allowed to buy this coupon twice");
			}
		}
		if (coupon.getAmount() == 0) {
			throw new NotAllowedException("You canno't buy a coupon when amount is 0");
		}
		if (coupon.getEnd_date().before(new Date())) {
			throw new NotAllowedException("This coupon is already expired");
		}
		customer.addCoupon(coupon);
		System.out.println("coupon " + coupon.getId() + " amount before purchase: " + coupon.getAmount());
		coupon.setAmount(coupon.getAmount() - 1);
		System.out.println("coupon " + coupon.getId() + " amount after purchase: " + coupon.getAmount());
		customerRepository.saveAndFlush(customer);
	}

	public void deleteCouponPurchase(int couponID) throws NotExistException {
		if (couponRepository.existsById(couponID) == false) {
			throw new NotExistException("The Coupon doesn't exists in the system");
		}
		Customer customer = customerRepository.getOne(customerID);
		List<Coupon> customerCoupons = customer.getCoupons();
		for (Coupon coupon : customerCoupons) {
			if (coupon.getId() == couponID) {
				customerCoupons.remove(coupon);
				customer.setCoupons(customerCoupons);
				customerRepository.saveAndFlush(customer);
				break;
			}
		}
	}

	public List<Coupon> getCustomerCoupons() {
		return customerRepository.getOne(customerID).getCoupons();
	}

	public List<Coupon> getCustomerCoupons(Category category) {
		List<Coupon> coupons = customerRepository.getOne(customerID).getCoupons();
		List<Coupon> temp = new ArrayList<Coupon>();
		for (int i = 0; i < coupons.size(); i++) {
			if (coupons.get(i).getCategoryID().equals(category)) {
				temp.add(coupons.get(i));
			}
		}

		return temp;
	}

	public List<Coupon> getCustomerCoupons(double maxPrice) {
		List<Coupon> coupons = customerRepository.getOne(customerID).getCoupons();
		List<Coupon> temp = new ArrayList<Coupon>();
		for (int i = 0; i < coupons.size(); i++) {
			if (coupons.get(i).getPrice() < maxPrice) {
				temp.add(coupons.get(i));
			}
		}
		return temp;
	}

	public Customer getCustomerDetailes() {
		return customerRepository.getOne(customerID);
	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public int getCustomerIdByEmailAndPassword(String email, String password) {
		return customerRepository.findByEmailAndPassword(email, password).getId();
	}

	public Customer getCustomerByEmailAndPassword(String email, String password) {
		return customerRepository.findByEmailAndPassword(email, password);
	}

	@Override
	public String toString() {
		return "CustomerService login successfully!!";
	}

}
