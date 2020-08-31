package com.johnbryce.app.dbdao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.johnbryce.app.beans.Coupon;
import com.johnbryce.app.repo.CouponRepository;

@Repository
public class CouponDBDAO {

	@Autowired
	private CouponRepository couponRepository;

	public boolean isCouponExists(int couponID) {
		return couponRepository.existsById(couponID);
	}

	public void addCoupon(Coupon coupon) {
		couponRepository.save(coupon);
	}

	public void updateCoupon(Coupon coupon) {
		couponRepository.saveAndFlush(coupon);
	}

	public void deleteCoupon(Coupon coupon) {
		couponRepository.delete(coupon);
	}

	public Coupon getOneCoupon(int id) {
		return couponRepository.getOne(id);
	}

	public List<Coupon> getAllCoupons() {
		return couponRepository.findAll();
	}

	public void deleteCouponVs(int couponID) {
		couponRepository.deleteCouponVsCustomers(couponID);
	}

}
