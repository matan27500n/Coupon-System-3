package com.johnbryce.app.manager;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.johnbryce.app.beans.Customer;
import com.johnbryce.app.service.ClientService;
import com.johnbryce.app.service.CustomerService;

@Component
public class TokenManager {
	
	@Autowired
	private HashMap<String, CustomerService> tokensInMemory;
	
	public String addToken(ClientService clientService) {
		String token = UUID.randomUUID().toString();
		//tokensInMemory.put(token, new Customer(clientService,System.currentTimeMillis()));
		return token;
	}
}
