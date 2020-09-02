package com.johnbryce.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.johnbryce.app.beans.Company;
import com.johnbryce.app.beans.Coupon;
import com.johnbryce.app.dbdao.CompanyDBDAO;
import com.johnbryce.app.dbdao.CouponDBDAO;
import com.johnbryce.app.exceptions.NotAllowedException;
import com.johnbryce.app.exceptions.NotExistException;

import lombok.Getter;
import lombok.Setter;

import com.johnbryce.app.beans.Category;

@Service
@Scope("prototype")
@Getter
@Setter
public class CompanyService extends ClientService {

	private int companyID;

	@Autowired
	private CompanyDBDAO companyDBDAO;

	@Autowired
	private CouponDBDAO couponDBDAO;

	@Override
	public boolean login(String email, String password) {
		if (companyDBDAO.isCompanyExistsByEmailAndPassword(email, password) == true) {
			this.companyID = companyDBDAO.getCompanyByEmailAndPassword(email, password).getId();
			return true;
		}
		return false;
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
		couponDBDAO.addCoupon(coupon);
	}

	public void updateCoupon(Coupon coupon) throws NotExistException {
		if (couponDBDAO.isCouponExists(coupon.getId()) == false) {
			throw new NotExistException("The Coupon doesn't exists in the system");
		}
		couponDBDAO.updateCoupon(coupon);
	}

	public void deleteCoupon(int couponID) throws NotExistException {
		if (couponDBDAO.isCouponExists(couponID) == false) {
			throw new NotExistException("The Coupon doesn't exists in the system");
		}
		couponDBDAO.deleteCoupon(couponDBDAO.getOneCoupon(couponID));
		int id = couponDBDAO.getOneCoupon(couponID).getCompanyID();
		companyDBDAO.updateCompany(companyDBDAO.getOneCompany(id));
	}

	public List<Coupon> getAllCompanyCoupons() {
		return couponRepository.findByCompanyID(companyID);
	}

	public List<Coupon> getCompanyCoupons(Category category) {
		return couponRepository.findByCategoryID(category);
	}

	public List<Coupon> getCompanyCoupons(double maxPrice) {
		return couponRepository.findByPriceLessThan(maxPrice);
	}

	public Company getCompanyDetailes() {
		return companyDBDAO.getOneCompany(companyID);
	}

	@Override
	public String toString() {
		return "CompanyService login successfully!!";
	}

	public void printWrongMessage() {
		System.out.print("The detailes are wrong,CompanyService login failed...");
	}

	public int getcompanyIdByEmailAndPassword(String email, String password) {
		return companyDBDAO.getCompanyByEmailAndPassword(email, password).getId();
	}

}
