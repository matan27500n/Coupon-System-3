package com.johnbryce.app.security;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.johnbryce.app.exceptions.NotExistException;
import com.johnbryce.app.rest.AdminController;
import com.johnbryce.app.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenManager {

	@Autowired
	private HashMap<String, CustomSession> tokensInMemory;

	@Autowired
	private AdminController adminController;

	public String addToken(ClientService clientService) {
		String token = UUID.randomUUID().toString();
		tokensInMemory.put(token, new CustomSession(clientService, System.currentTimeMillis()));
		System.out.println("tokensInMemory: " + tokensInMemory);
		return token;
	}

	public long getTimestamp(String token) {
		return tokensInMemory.getOrDefault(token, null).getDate();
	}

	public ClientService getClientService(String token) {
		return tokensInMemory.getOrDefault(token, null).getClientService();
	}

	public boolean isTokenExist(String token) throws NotExistException {
		for (Map.Entry<String, CustomSession> entry : tokensInMemory.entrySet()) {
			if (entry.getKey().equals(token)) {
				return true;
			}
		}
		throw new NotExistException("The token is not exists in the system");
	}

	public void removeToken(String token) {
		tokensInMemory.remove(token);
	}
}
