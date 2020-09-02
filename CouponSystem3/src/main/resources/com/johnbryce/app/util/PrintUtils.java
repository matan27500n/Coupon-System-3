package com.johnbryce.app.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johnbryce.app.beans.Company;
import com.johnbryce.app.beans.Coupon;
import com.johnbryce.app.beans.Customer;
import com.johnbryce.app.service.AdminService;

@Service
public class PrintUtils {

	@Autowired
	private AdminService adminService;

	public void printCompanies() {
		List<Company> companies = adminService.getAllCompanies();
		System.out.printf("%5s %10s %20s %20s %20s", "CompanyID", "Name", "Email", "Password", "Coupons");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		for (Company company : companies) {
			System.out.format("%5s %15s %25s %10s %15s", company.getId(), company.getName(), company.getEmail(),
					company.getPassword(), company.getCoupons());
			System.out.println();
		}
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println();
	}

	public void printOneCompany(Company company) {
		System.out.printf("%5s %10s %20s %20s %20s", "CompanyID", "Name", "Email", "Password", "Coupons");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.format("%5s %15s %25s %15s %10s", company.getId(), company.getName(), company.getEmail(),
				company.getPassword(), company.getCoupons());
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println();
	}

	public void printCustomers() {
		List<Customer> customers = adminService.getAllCustomers();
		System.out.printf("%5s %10s %10s %15s %17s %17s", "customerID", "firstName", "lastName", "email", "password",
				"Coupons");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		for (Customer customer : customers) {
			System.out.format("%5s %10s %13s %20s %17s %17s", customer.getId(), customer.getFirst_name(),
					customer.getLast_name(), customer.getEmail(), customer.getPassword(), customer.getCoupons());
			System.out.println();
		}
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println();
	}

	public void printOneCustomer(Customer customer) {
		System.out.printf("%5s %10s %10s %15s %17s %17s", "customerID", "firstName", "lastName", "email", "password",
				"Coupons");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.format("%5s %10s %13s %20s %17s %17s", customer.getId(), customer.getFirst_name(),
				customer.getLast_name(), customer.getEmail(), customer.getPassword(), customer.getCoupons());
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println();
	}

	public void printCoupons() {
		List<Coupon> coupons = adminService.getAllCoupons();
		System.out.printf("%5s %5s %5s %5s %15s %22s %20s %17s %7s %10s", "couponID", "companyID", "categoryID",
				"title", "description", "startDate", "endDate", "amount", "price", "image");
		System.out.println();
		System.out.println(
				"-------------------------------------------------------------------------------------------------------------------------------------------");
		for (Coupon coupon : coupons) {
			System.out.format("%5s %5s %15s %5s %25s %22s %22s %5s %7s %15s", coupon.getId(), coupon.getCompanyID(),
					coupon.getCategoryID(), coupon.getTitle(), coupon.getDescription(), coupon.getStart_date(),
					coupon.getEnd_date(), coupon.getAmount(), coupon.getPrice(), coupon.getImage());
			System.out.println();
		}
		System.out.println(
				"-------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
	}

	public void printOneCoupon(Coupon coupon) {
		System.out.printf("%5s %5s %5s %5s %15s %22s %20s %17s %7s %10s", "couponID", "companyID", "categoryID",
				"title", "description", "startDate", "endDate", "amount", "price", "image");
		System.out.println();
		System.out.println(
				"-------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.format("%5s %5s %15s %5s %25s %22s %22s %5s %7s %15s", coupon.getId(), coupon.getCompanyID(),
				coupon.getCategoryID(), coupon.getTitle(), coupon.getDescription(), coupon.getStart_date(),
				coupon.getEnd_date(), coupon.getAmount(), coupon.getPrice(), coupon.getImage());
		System.out.println();
		System.out.println(
				"-------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
	}

	public void seperateLines(String name) {
		System.out.println();
		for (int i = 0; i < 80; i++) {
			System.out.print("-");
		}
		System.out.println();
		System.out.println("--------------------------------- " + name + "-----------------------------");
		for (int i = 0; i < 80; i++) {
			System.out.print("-");
		}
		System.out.println();
		System.out.println();
	}

}
