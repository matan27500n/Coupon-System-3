package com.johnbryce.app.schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.johnbryce.app.rest.AdminController;
import com.johnbryce.app.rest.ClientController;
import com.johnbryce.app.security.CustomSession;
import com.johnbryce.app.security.TokenManager;

import lombok.Getter;

@Component
@Getter
public class TokenExpiredJob {

	@Autowired
	private HashMap<String, CustomSession> tokensInMemory;

	@Autowired
	private TokenManager tokenManager;

	/*
	 * @Scheduled(fixedRate = 1000 * 60) public void deleteOldTokens() { for
	 * (Map.Entry<String, CustomSession> entry : tokensInMemory.entrySet()) { if
	 * (entry.getValue() == null) { return; } CustomSession customSession =
	 * entry.getValue(); String token = entry.getKey(); if
	 * (tokensInMemory.containsKey(token)) { tokensInMemory.remove(token,
	 * customSession); System.out.println("deleted old token"); } }
	 * 
	 * }
	 */

	@Scheduled(fixedRate = 1000 * 60 * 30)
	public void removeExpiredToken() {
		for (Map.Entry<String, CustomSession> entry : tokensInMemory.entrySet()) {
			CustomSession customSession = entry.getValue();
			// Date now = new Date(System.currentTimeMillis() +
			// TimeUnit.MINUTES.toMillis(30));
			String token = entry.getKey();
			// Date other = new Date(tokenManager.getTimestamp(token));
			if (System.currentTimeMillis() >= tokenManager.getTimestamp(token) + TimeUnit.MINUTES.toMillis(30)) {
				tokensInMemory.remove(token, customSession);
				System.out.println("deleting token successfully");
			}
		}

	}

}
