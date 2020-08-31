package com.johnbryce.app.dbdao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.johnbryce.app.beans.Customer;
import com.johnbryce.app.repo.CustomerRepository;

@Repository
public class CustomerDBDAO {

	@Autowired
	private CustomerRepository customerRepository;

	public boolean isCustomerExists(String email, String password) {
		if (customerRepository.findByEmailAndPassword(email, password) == null) {
			return false;
		}
		return true;
	}

	public void addCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	public void updateCustomer(Customer customer) {
		customerRepository.saveAndFlush(customer);
	}

	public void deleteCustomer(Customer customer) {
		customerRepository.delete(customer);
	}

	public Customer getOneCustomer(int id) {
		return customerRepository.getOne(id);
	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
}
