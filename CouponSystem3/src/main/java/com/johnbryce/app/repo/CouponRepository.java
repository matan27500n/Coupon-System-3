package com.johnbryce.app.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.johnbryce.app.beans.Category;
import com.johnbryce.app.beans.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	boolean findById(int couponID);

	List<Coupon> findByCompanyID(int companyID);

	List<Coupon> findByCategoryID(Category category);

	List<Coupon> findByPriceLessThan(double price);

	@Modifying
	@Transactional
	@Query(value = "delete FROM couponsystem2.customers_coupons where coupons_id =:couponID", nativeQuery = true)
	void deleteCouponVsCustomers(int couponID);
}
