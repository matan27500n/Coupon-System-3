package com.johnbryce.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johnbryce.app.exceptions.NotAllowedException;
import com.johnbryce.app.repo.CompanyRepository;
import com.johnbryce.app.repo.CouponRepository;
import com.johnbryce.app.repo.CustomerRepository;

@Service
public abstract class ClientService {

	@Autowired
	protected CompanyRepository companyRepository;
	@Autowired
	protected CustomerRepository customerRepository;
	@Autowired
	protected CouponRepository couponRepository;

	public abstract boolean login(String email, String password) throws NotAllowedException;
}
