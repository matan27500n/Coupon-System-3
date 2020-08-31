package com.johnbryce.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johnbryce.app.beans.Company;
import com.johnbryce.app.beans.Coupon;
import com.johnbryce.app.beans.Customer;
import com.johnbryce.app.dbdao.CompanyDBDAO;
import com.johnbryce.app.dbdao.CouponDBDAO;
import com.johnbryce.app.dbdao.CustomerDBDAO;
import com.johnbryce.app.exceptions.AlreadyExitsException;
import com.johnbryce.app.exceptions.NotAllowedException;
import com.johnbryce.app.exceptions.NotExistException;

@Service
public class AdminService extends ClientService {
	@Autowired
	private CompanyDBDAO companyDBDAO;

	@Autowired
	private CouponDBDAO couponDBDAO;

	@Autowired
	private CustomerDBDAO customerDBDAO;

	@Override
	public boolean login(String email, String password) {
		return email.equals("admin@admin.com") && password.equals("admin");
	}

	public boolean isCompanyExists(int id) {
		return companyDBDAO.isCompanyExists(id);
	}

	public void addCompany(Company company) throws AlreadyExitsException {
		if (companyRepository.findByEmail(company.getEmail()) != null
				|| (companyRepository.findByName(company.getName()) != null)) {
			throw new AlreadyExitsException();
		}
		companyDBDAO.addCompany(company);
	}

	public void updateCompany(Company company) throws NotExistException, NotAllowedException {
		if (companyRepository.findById(company.getId()) == null) {
			throw new NotExistException("The id doesn't exists");
		}
		Company company2 = companyRepository.getOne(company.getId());
		if (!company.getName().equals(company2.getName())) {
			throw new NotAllowedException("You are not allowed to change name company");
		}
		companyDBDAO.updateCompany(company);
	}

	public void deleteCompany(int companyID) throws NotExistException {
		if (!companyRepository.existsById(companyID)) {
			throw new NotExistException("The company doesn't exists in the sysyem");
		}
		List<Coupon> originalCoupons = couponDBDAO.getAllCoupons();
		for (Coupon coupon : originalCoupons) {
			if (coupon.getCompanyID() == companyID) {
				couponDBDAO.deleteCoupon(coupon);
			}
		}
		companyDBDAO.deleteCompany(companyDBDAO.getOneCompany(companyID));
	}

	public List<Company> getAllCompanies() {
		return companyDBDAO.getAllCompanies();
	}

	public Company getOneCompany(int companyID) {
		return companyDBDAO.getOneCompany(companyID);
	}

	public void addCustomer(Customer customer) throws AlreadyExitsException {
		if (customerRepository.findByEmail(customer.getEmail()) != null) {
			throw new AlreadyExitsException("The customer is already exists");
		}
		customerDBDAO.addCustomer(customer);
	}

	public void updateCustomer(Customer customer) throws NotAllowedException {
		if (customerRepository.findById(customer.getId()) == null) {
			throw new NotAllowedException("The id doesn't exists in the system");
		}
		customerDBDAO.updateCustomer(customer);
	}

	public void deleteCustomer(Customer customer) throws NotExistException {
		if (customerRepository.findById(customer.getId()) == null) {
			throw new NotExistException("The ID doesn't exists in the system");
		}
		customerDBDAO.deleteCustomer(customer);
	}

	public List<Customer> getAllCustomers() {
		return customerDBDAO.getAllCustomers();
	}

	public Customer getOneCustomer(int customerID) {
		return customerDBDAO.getOneCustomer(customerID);
	}

	public void addCoupon(Coupon coupon) {
		couponDBDAO.addCoupon(coupon);
	}

	public void updateCoupon(Coupon coupon) {
		couponDBDAO.updateCoupon(coupon);
	}

	public void deleteCoupon(Coupon coupon) {
		couponDBDAO.deleteCoupon(coupon);
	}

	public List<Coupon> getAllCoupons() {
		return couponDBDAO.getAllCoupons();
	}

	public Coupon getOneCoupon(int couponID) {
		return couponDBDAO.getOneCoupon(couponID);
	}

	public Company getCompanyByEmailAndPassword(String email, String password) throws NotExistException {
		if (companyDBDAO.getCompanyByEmailAndPassword(email, password) == null) {
			throw new NotExistException("The company doesn't exists by this detailes");
		}
		return companyDBDAO.getCompanyByEmailAndPassword(email, password);
	}

	public boolean isCouponExists(int id) {
		return couponDBDAO.isCouponExists(id);
	}

	public boolean isCustomerExists(String email, String password) {
		return customerDBDAO.isCustomerExists(email, password);
	}

	@Override
	public String toString() {

		return "AdminService login successfully!!";
	}

	public void printWrongMessage() {
		System.out.print("The detailes are wrong, AdminService login failed..");
	}

}
