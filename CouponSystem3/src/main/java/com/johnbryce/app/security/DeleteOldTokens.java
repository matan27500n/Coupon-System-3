package com.johnbryce.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeleteOldTokens {

	@Autowired
	private TokenManager tokenManager;

	@Scheduled(fixedDelay = 1000 * 60 * 30)
	public void deleteTokensExpired() {

	}
}
