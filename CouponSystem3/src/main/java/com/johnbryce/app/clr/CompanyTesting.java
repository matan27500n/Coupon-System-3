package com.johnbryce.app.clr;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.johnbryce.app.beans.Company;
import com.johnbryce.app.service.AdminService;
import com.johnbryce.app.service.CompanyService;
import com.johnbryce.app.util.PrintUtils;

@Component
@Order(value = 1)
public class CompanyTesting implements CommandLineRunner {

	@Autowired
	private PrintUtils printUtils;

	@Autowired
	private AdminService adminService;

	@Autowired
	private CompanyService companyService;

	@Override
	public void run(String... args) throws Exception {
		printUtils.seperateLines("Company Methods:");

		Company c1 = new Company("Coca cola", "coca@gmail.com", "1111");
		Company c2 = new Company("Pepsi", "pepsi@gmail.com", "1234");
		Company c3 = new Company("Super Pharm", "superpharm@gmail.com", "3567");
		Company c4 = new Company("Good Pharm", "goodpharm@gmail.com", "5556");
		Company c5 = new Company("Burger King", "burgerking@gmail.com", "7189");
		List<Company> companyArrays = Arrays.asList(c1, c2, c3, c4, c5);

		// add companies
		for (int i = 0; i < companyArrays.size(); i++) {
			adminService.addCompany(companyArrays.get(i));
		}
		printUtils.printCompaniesWithOutCoupon(adminService.getAllCompanies());

		// company login - success
		System.out.println("company loging success? : " + companyService.login("pepsi@gmail.com", "1234"));
		// company login - fails
		System.out.println("company loging success? : " + companyService.login("pepsi@gmail.il", "6666"));
		System.out.println();

		// check if company exists - return true
		System.out.println("company exists? " + adminService.isCompanyExists(2));
		// check if company exists - return false
		System.out.println("company exists? " + adminService.isCompanyExists(11));

		// delete company
		/*adminService.deleteCompany(c5.getId());
		System.out.println();
		System.out.println("The companies after deleting company " + c5.getId() + " :");
		printUtils.printCompaniesWithOutCoupon(adminService.getAllCompanies());*/

		// get one company
		System.out.println("Get company 1:");
		printUtils.printOneCompanyWithOutCoupon(adminService.getOneCompany(1));
		
		System.out.println("The id of company cola :" + companyService.getCompanyIdByEmailAndPassword("coca@gmail.com", "1111"));

		// get company by email and password
		System.out.println("Get company by email and password:");
		printUtils.printOneCompanyWithOutCoupon(
				adminService.getCompanyByEmailAndPassword("superpharm@gmail.com", "3567"));
	}
}
