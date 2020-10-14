package com.johnbryce.app.clr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.johnbryce.app.beans.Category;
import com.johnbryce.app.beans.Company;
import com.johnbryce.app.beans.Coupon;
import com.johnbryce.app.exceptions.NotAllowedException;
import com.johnbryce.app.exceptions.NotExistException;
import com.johnbryce.app.schedule.CouponExpirationDailyJob;
import com.johnbryce.app.security.ClientType;
import com.johnbryce.app.security.LoginManager;
import com.johnbryce.app.service.AdminService;
import com.johnbryce.app.service.CustomerService;
import com.johnbryce.app.util.PrintUtils;
import com.johnbryce.app.util.Utilities;

@Component
@Order(value = 3)
public class CouponTesting implements CommandLineRunner {

	@Autowired
	private PrintUtils printUtils;

	@Autowired
	private AdminService adminService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CouponExpirationDailyJob couponExpirationDailyJob;

	@Autowired
	private LoginManager loginManager;

	public void addCouponToCompany(Coupon coupon) throws NotExistException, NotAllowedException {
		if (!adminService.isCompanyExists(coupon.getCompanyID())) {
			throw new NotExistException(
					"The company is not exists in the system, you canno't add coupon to the company");
		}
		adminService.addCoupon(coupon);
		Company company = adminService.getOneCompany(coupon.getCompanyID());
		company.addCoupon(coupon);
		company.setCoupons(Arrays.asList(coupon));
		adminService.updateCompany(company);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void run(String... args) throws Exception {
		printUtils.seperateLines("Coupon Methods");

		// Dates for coupon 1:
		Date d1 = new Date(2020, 9, 14);
		Date d2 = new Date(2021, 3, 12);
		// Dates for coupon 2:
		Date d3 = new Date(2020, 5, 22);
		Date d4 = new Date(2020, 8, 3);
		// Dates for coupon 3:
		Date d5 = new Date(2020, 11, 11);
		Date d6 = new Date(2020, 12, 22);
		// Dates for coupon 4:
		Date d7 = new Date(2021, 1, 1);
		Date d8 = new Date(2021, 2, 1);
		// Dates for coupon 5:
		Date d9 = new Date(2021, 3, 7);
		Date d10 = new Date(2021, 5, 10);

		// create the coupons
		List<Coupon> coupons = new ArrayList<>();
		Coupon c1 = new Coupon(1, Category.Drinks, "Sale", "30%", Utilities.convertUtilDateToSQL(d1),
				Utilities.convertUtilDateToSQL(d2), 15, 15,
				"https://supply.layam.com/pub/media/catalog/product/cache/e4d64343b1bc593f1c5348fe05efa4a6/3/7/3720208_1.jpg");
		addCouponToCompany(c1);
		coupons.add(c1);
		Coupon c2 = new Coupon(2, Category.Drinks, "Discount", "50% on all the bottles",
				Utilities.convertUtilDateToSQL(d3), Utilities.convertUtilDateToSQL(d4), 150, 3,
				"data:image/jpeg;base64,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");
		addCouponToCompany(c2);
		coupons.add(c2);
		Coupon c3 = new Coupon(3, Category.Pharmacy, "10%", "10% on all the loations",
				Utilities.convertUtilDateToSQL(d5), Utilities.convertUtilDateToSQL(d6), 76, 8,
				"https://customer-service.xyz/wp-content/uploads/2019/12/%D7%A1%D7%95%D7%A4%D7%A8-%D7%A4%D7%90%D7%A8%D7%9D-%D7%A9%D7%99%D7%A8%D7%95%D7%AA-%D7%9C%D7%A7%D7%95%D7%97%D7%95%D7%AA.jpg");
		addCouponToCompany(c3);
		coupons.add(c3);
		Coupon c4 = new Coupon(4, Category.Pharmacy, "1+1", "1+1 on all the Axe", Utilities.convertUtilDateToSQL(d7),
				Utilities.convertUtilDateToSQL(d8), 200, 10,
				"data:image/png;base64,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");
		addCouponToCompany(c4);
		coupons.add(c4);
		Coupon c5 = new Coupon(1, Category.Drinks, "Sale All", "26% discount", Utilities.convertUtilDateToSQL(d9),
				Utilities.convertUtilDateToSQL(d10), 10, 10,
				"https://d3m9l0v76dty0.cloudfront.net/system/photos/5118518/original/6eb9e4dd96bacf33d7006fc17d450d92.jpg");
		addCouponToCompany(c5);
		coupons.add(c5);
		Coupon c6 = new Coupon(5, Category.Fast_food, "15%", "15% discount on all the menu",
				Utilities.convertUtilDateToSQL(d5), Utilities.convertUtilDateToSQL(d6), 89, 19,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/7/79/Burger_King_logo.svg/1200px-Burger_King_logo.svg.png");
		addCouponToCompany(c6);
		coupons.add(c6);

		System.out.println("Companies full details:");
		printUtils.printCompanies(coupons, adminService.getAllCompanies());

		System.out.println("The coupons are: ");
		printUtils.printCoupons(adminService.getAllCoupons());

		// coupon exists? - true
		System.out.println("does coupon exists? " + adminService.isCouponExists(1));
		// coupon exists? - false
		System.out.println("does coupon exists? " + adminService.isCouponExists(10));
		System.out.println();

		// update coupon
		c1.setDescription("30% sale on all the cans");
		adminService.updateCoupon(c1);
		System.out.println("coupon " + c1.getId() + " after update: ");
		printUtils.printOneCoupon(c1);

		// delete coupon
		/*
		 * adminService.deleteCoupon(c4.getId());
		 * System.out.println("coupons after delete coupon " + c4.getId() + ":");
		 * printUtils.printCoupons(adminService.getAllCoupons());
		 */

		// get one coupon
		System.out.println("get coupon 3:");
		printUtils.printOneCoupon(adminService.getOneCoupon(3));

		// purchase coupon
		printUtils.seperateLines("Coupons purchase and delete:");
		customerService.setCustomerID(1);
		customerService.purchaseCoupon(c1);
		printUtils.printOneCustomer(adminService.getOneCustomer(1));
		customerService.setCustomerID(2);
		customerService.purchaseCoupon(c3);
		printUtils.printOneCustomer(adminService.getOneCustomer(2));
		System.out.println();
		printUtils.printCustomers(adminService.getAllCustomers());

		// delete coupon and his purchases
		// List<Customer> customers = customerService.getAllCustomers();
		// for (Customer customer : customers) {
		// if (customer.getCoupons() == c1) {
		// customer.getCoupons().remove(c1);
		// adminService.updateCustomer(customer);
		// }
		// }
		// adminService.deleteCoupon(c1.getId());
		// customerService.deleteCustomersVScoupons(c1.getId());
		// System.out.println("Print customers details after deleting coupons:");
		// printUtils.printCustomers(adminService.getAllCustomers());

		// login manager successfully
		printUtils.seperateLines("Login Manager:");
		System.out.println("Login manager success:");
		System.out.println(loginManager.login("admin@admin.com", "admin", ClientType.Administrator));
		System.out.println(loginManager.login("topaz12@gmail.com", "0000", ClientType.Customer));
		System.out.println(loginManager.login("pepsi@gmail.com", "1234", ClientType.Company));

		System.out.println();
		printUtils.printCompanies(adminService.getAllCoupons(), adminService.getAllCompanies());

		// coupon expired check
		printUtils.seperateLines("CouponExpired Running:");
		System.out.println("The coupons before deleting: ");
		printUtils.printCoupons(adminService.getAllCoupons());
		couponExpirationDailyJob.deleteCouponsExpired();
		System.out.println("The coupons after deleting:");
		printUtils.printCoupons(adminService.getAllCoupons());

	}

}
