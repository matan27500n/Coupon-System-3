package com.johnbryce.app.security;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.johnbryce.app.beans.LoginResponse;
import com.johnbryce.app.exceptions.NotExistException;
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

	public String addToken(ClientService clientService) {
		String token = UUID.randomUUID().toString();
		tokensInMemory.put(token, new CustomSession(clientService, System.currentTimeMillis()));
		return token;
	}
	
	public long getTimestamp(String token) {
		return tokensInMemory.getOrDefault(token, null).getDate();
	}

	public ClientService getClientService(String token) {
		return tokensInMemory.getOrDefault(token, null).getClientService();
	}

	public void deleteOldTokens() {
		for (Map.Entry<String, CustomSession> entry : tokensInMemory.entrySet()) {
			CustomSession customSession = entry.getValue();
		}
	}

	public boolean isTokenExist(String token) throws NotExistException {
		CustomSession customSession = tokensInMemory.get(token);
		if (customSession != null) {
			return true;
		}
		throw new NotExistException("Token not found, please try again");
	}
}
