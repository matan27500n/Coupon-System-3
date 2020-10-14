package com.johnbryce.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.johnbryce.app.beans.Company;
import com.johnbryce.app.beans.Coupon;
import com.johnbryce.app.exceptions.NotAllowedException;
import com.johnbryce.app.exceptions.NotExistException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.johnbryce.app.beans.Category;

@Service
@Scope("prototype")
@Getter
@Setter
@NoArgsConstructor
public class CompanyService extends ClientService {

	private int companyID;

	@Autowired
	private AdminService adminService;

	@Override
	public boolean login(String email, String password) {
		if (companyRepository.findByEmailAndPassword(email, password) == null) {
			return false;
		}
		return true;
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

	public void addCoupon(Coupon coupon) throws NotAllowedException {
		List<Coupon> coupons = couponRepository.findAll();
		for (Coupon coupon2 : coupons) {
			if (coupon2.getCompanyID() == coupon.getCompanyID()) {
				if (coupon2.getTitle().equals(coupon.getTitle())) {
					throw new NotAllowedException("the title is the same between the 2 companies");
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

	public void deleteCoupon(int couponID) throws NotExistException {
		if (couponRepository.existsById(couponID) == false) {
			throw new NotExistException("The Coupon doesn't exists in the system");
		}
		couponRepository.delete(couponRepository.getOne(couponID));
		// companyRepository.saveAndFlush(companyRepository.getOne(couponRepository.getOne(couponID).getCompanyID()));
	}

	public Coupon getOneCoupon(int couponID) throws NotExistException {
		return couponRepository.getOne(couponID);
	}

	public List<Coupon> getCompanyCoupons() {
		return couponRepository.findByCompanyID(companyID);
	}

	public List<Coupon> getCompanyCoupons(Category category) throws NotExistException {
		System.out.println("Company id: " + companyID);
		System.out.println("category: " + category);
		List<Coupon> coupons = adminService.getAllCoupons();
		System.out.println("coupons length: " + coupons.size());
		List<Coupon> temp = new ArrayList<Coupon>();
		for (int i = 0; i < coupons.size(); i++) {
			if (coupons.get(i).getCategoryID().equals(category) && coupons.get(i).getCompanyID() == companyID) {
				temp.add(coupons.get(i));
			}
		}
		return temp;
	}

	public List<Coupon> getCompanyCoupons(double maxPrice) {
		// List<Coupon> coupons = adminService.getAllCoupons();
		List<Coupon> coupons = couponRepository.findAll();
		List<Coupon> temp = new ArrayList<Coupon>();
		for (int i = 0; i < coupons.size(); i++) {
			if (coupons.get(i).getPrice() < maxPrice && coupons.get(i).getCompanyID() == companyID) {
				temp.add(coupons.get(i));
			}
		}
		return temp;
	}

	public Company getCompanyDetailes() {
		return companyRepository.getOne(companyID);
	}

	public Company getCompanyByEmailAndPassword(String email, String password) {
		return companyRepository.findByEmailAndPassword(email, password);
	}

	public int getCompanyIdByEmailAndPassword(String email, String password) {
		return companyRepository.findByEmailAndPassword(email, password).getId();
	}

	@Override
	public String toString() {
		return "CompanyService login successfully!!";
	}
}
