package com.johnbryce.app.service;

import java.util.List;
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

	@Override
	public boolean login(String email, String password) {
		if (companyRepository.findByEmailAndPassword(email, password) == null) {
			return false;
		}
		return true;
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
		companyRepository.saveAndFlush(companyRepository.getOne(couponRepository.getOne(couponID).getCompanyID()));
	}

	public List<Coupon> getCompanyCoupons() {
		return couponRepository.findByCompanyID(companyID);
	}

	public List<Coupon> getCompanyCoupons(Category category) {
		System.out.println(couponRepository.findByCategoryID(category));
		return couponRepository.findByCategoryID(category);
	}

	public List<Coupon> getCompanyCoupons(double maxPrice) {
		return couponRepository.findByPriceLessThan(maxPrice);
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
