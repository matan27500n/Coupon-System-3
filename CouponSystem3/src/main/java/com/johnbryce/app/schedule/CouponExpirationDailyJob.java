package com.johnbryce.app.schedule;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.johnbryce.app.beans.Coupon;
import com.johnbryce.app.service.AdminService;

@Component
public class CouponExpirationDailyJob extends Thread {

	// SLEEP = 24*60*60*100 -> 24 hours
	private static final long SLEEP = 24 * 60 * 60 * 100;

	@Autowired
	AdminService adminService;

	private boolean quit = false;

	public void run() {
		while (!quit) {
			List<Coupon> coupons = adminService.getAllCoupons();
			for (int i = 0; i < coupons.size(); i++) {
				if (coupons.get(i).getEnd_date().before(new Date())) {
					System.out.println("Going to delete coupon: " + coupons.get(i).getId());
					adminService.deleteCoupon(coupons.get(i));
				}
			}

			try {
				Thread.sleep(SLEEP);
				System.out.println("sleeping...");
				// this.quit = true;
			} catch (InterruptedException e) {
				e.getMessage();
			}
		}
	}

	public void stopRunning() {
		this.quit = true;
	}
}
