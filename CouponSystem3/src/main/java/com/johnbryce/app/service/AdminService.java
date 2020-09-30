package com.johnbryce.app.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.johnbryce.app.beans.Company;
import com.johnbryce.app.beans.Coupon;
import com.johnbryce.app.beans.Customer;
import com.johnbryce.app.exceptions.AlreadyExitsException;
import com.johnbryce.app.exceptions.NotAllowedException;
import com.johnbryce.app.exceptions.NotExistException;

@Service
public class AdminService extends ClientService {

	@Override
	public boolean login(String email, String password) {
		return email.equals("admin@admin.com") && password.equals("admin");
	}

	public boolean isCompanyExists(int id) {
		return companyRepository.existsById(id);
	}

	public void addCompany(Company company) throws AlreadyExitsException {
		if (companyRepository.findByNameAndEmail(company.getName(), company.getEmail()) != null) {
			throw new AlreadyExitsException();
		}
		if (companyRepository.findByEmail(company.getEmail()) != null) {
			throw new AlreadyExitsException();
		}
		companyRepository.save(company);
	}

	public void updateCompany(Company company) throws NotExistException, NotAllowedException {
		if (companyRepository.findById(company.getId()) == null) {
			throw new NotExistException("The id doesn't exists");
		}
		Company company2 = companyRepository.getOne(company.getId());
		if (!company.getName().equals(company2.getName())) {
			throw new NotAllowedException("You are not allowed to change name company");
		}
		companyRepository.saveAndFlush(company);
	}

	public void deleteCompany(int companyID) throws NotExistException {
		if (!companyRepository.existsById(companyID)) {
			throw new NotExistException("The company doesn't exists in the sysyem");
		}
		List<Coupon> originalCoupons = couponRepository.findAll();
		for (Coupon coupon : originalCoupons) {
			if (coupon.getCompanyID() == companyID) {
				couponRepository.delete(coupon);
			}
		}
		companyRepository.delete(companyRepository.getOne(companyID));
	}

	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	public Company getOneCompany(int companyID) throws NotExistException {
		if (companyRepository.existsById(companyID) == false) {
			throw new NotExistException("The company is not exists by this id");
		}
		return companyRepository.getOne(companyID);
	}

	public void addCustomer(Customer customer) throws AlreadyExitsException {
		if (customerRepository.findByEmail(customer.getEmail()) != null) {
			throw new AlreadyExitsException("The customer is already exists");
		}
		customerRepository.save(customer);
	}

	public void updateCustomer(Customer customer) throws NotAllowedException {
		if (customerRepository.findById(customer.getId()) == null) {
			throw new NotAllowedException("The id doesn't exists in the system");
		}
		customerRepository.saveAndFlush(customer);
	}

	public void deleteCustomer(int id) throws NotExistException {
		if (customerRepository.getOne(id) == null) {
			throw new NotExistException("The ID doesn't exists in the system");
		}
		customerRepository.delete(customerRepository.getOne(id));
	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public Customer getOneCustomer(int customerID) throws NotExistException {
		if (!customerRepository.existsById(customerID)) {
			throw new NotExistException("The customer doesn't exists in the system");
		}
		return customerRepository.getOne(customerID);
	}

	public void addCoupon(Coupon coupon) throws NotAllowedException {
		List<Coupon> coupons = couponRepository.findAll();
		for (Coupon coupon2 : coupons) {
			if (coupon2.getCompanyID() == coupon.getCompanyID()) {
				if (coupon2.getTitle().equals(coupon.getTitle())) {
					throw new NotAllowedException("the title is the same between the 2 coupons");
				}
			}
		}
		couponRepository.save(coupon);
	}

	public void updateCoupon(Coupon coupon) throws NotExistException {
		if (couponRepository.existsById(coupon.getId()) == false) {
			throw new NotExistException("The Coupon doesn't exists in the system");
		}
		couponRepository.saveAndFlush(coupon);
	}

	public void deleteCoupon(int id) throws NotExistException {
		couponRepository.delete(couponRepository.getOne(id));
	}

	public List<Coupon> getAllCoupons() {
		return couponRepository.findAll();
	}

	public Coupon getOneCoupon(int couponID) throws NotExistException {
		return couponRepository.getOne(couponID);
	}

	public Company getCompanyByEmailAndPassword(String email, String password) throws NotExistException {
		if (companyRepository.findByEmailAndPassword(email, password) == null) {
			throw new NotExistException("The company doesn't exists by this detailes");
		}
		return companyRepository.findByEmailAndPassword(email, password);
	}

	public boolean isCouponExists(int id) {
		return couponRepository.existsById(id);
	}

	public boolean isCustomerExists(String email, String password) {
		if (customerRepository.findByEmailAndPassword(email, password) == null) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "AdminService login successfully!!";
	}

}
