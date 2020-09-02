package com.johnbryce.app.schedule;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.johnbryce.app.beans.Coupon;
import com.johnbryce.app.service.AdminService;
import com.johnbryce.app.util.PrintUtils;

@Component
public class CouponExpirationDailyJob extends Thread {
	@Autowired
	AdminService adminService;

	@Autowired
	PrintUtils printUtils;

	@Scheduled(fixedRate = 24 * 60 * 60 * 1000)
	public void deleteCouponsExpired() {
		List<Coupon> coupons = adminService.getAllCoupons();
		System.out.println("The coupons before checking: ");
		printUtils.printCoupons();
		for (int i = 0; i < coupons.size(); i++) {
			if (coupons.get(i).getEnd_date().before(new Date())) {
				System.out.println("Going to delete coupon: ");
				printUtils.printOneCoupon(coupons.get(i));
				adminService.deleteCoupon(coupons.get(i));
			}
		}
		System.out.println("The coupons after deleting:");
		printUtils.printCoupons();
	}
}
