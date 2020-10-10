package com.johnbryce.app.clr;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.johnbryce.app.beans.Customer;
import com.johnbryce.app.service.AdminService;
import com.johnbryce.app.service.CustomerService;
import com.johnbryce.app.util.PrintUtils;

@Component
@Order(value = 2)
public class CustomerTesting implements CommandLineRunner {

	@Autowired
	private PrintUtils printUtils;

	@Autowired
	private AdminService adminService;

	@Autowired
	private CustomerService customerService;

	@Override
	public void run(String... args) throws Exception {
		printUtils.seperateLines("Customer Methods:");

		Customer c1 = new Customer("Topaz", "Dali", "topaz@gmail.com", "0000");
		Customer c2 = new Customer("Matan", "Bruchim", "matan@gmail.com", "1111");
		Customer c3 = new Customer("Yossi", "Shakarof", "yossi@gmail.com", "2222");
		Customer c4 = new Customer("Lidor", "Mualem", "lidor@gmail.com", "3333");
		Customer c5 = new Customer("Barel", "Amar", "barel@gmail.con", "4444");
		List<Customer> custoArrays = Arrays.asList(c1, c2, c3, c4, c5);

		// adding the customer
		for (int i = 0; i < custoArrays.size(); i++) {
			adminService.addCustomer(custoArrays.get(i));
		}
		printUtils.printCustomersWithOutCoupon(adminService.getAllCustomers());

		// customer login - success
		System.out.println(
				"Customer login success?: " + customerService.login("topazdali@gmail.com", "topazTheQuine123"));
		// customer login - fails
		System.out.println(
				"Customer login success?: " + customerService.login("topazdali77@gmail.com", "topazTheQuine12345"));
		System.out.println();

		// customer exists - true
		System.out.println("customer exists? " + adminService.isCustomerExists("matanbru@gmail.com", "matanbru123"));
		// customer exists - false
		System.out
				.println("customer exists? " + adminService.isCustomerExists("matanbru334@gmail.com", "matanbru12345"));
		System.out.println();

		// update customer
		c1.setEmail("topaz12@gmail.com");
		adminService.updateCustomer(c1);
		System.out.println("After updating customer " + c1.getId() + " :");
		printUtils.printOneCustomerWithOutCoupon(c1);

		// delete customer
		/*adminService.deleteCustomer(c5.getId());
		System.out.println("customers detailes after deleting customer " + c5.getId() + ":");
		printUtils.printCustomersWithOutCoupon(adminService.getAllCustomers());*/

		// get one customer
		System.out.println("get customer 3:");
		printUtils.printOneCustomerWithOutCoupon((adminService.getOneCustomer(3)));
	}
}
