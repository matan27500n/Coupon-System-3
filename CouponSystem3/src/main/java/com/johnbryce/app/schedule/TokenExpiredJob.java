package com.johnbryce.app.schedule;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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

	@Scheduled(fixedRate = 1000 * 60 * 5)
	public void removeExpiredCoupons() {
		System.out.println(Arrays.asList(tokensInMemory));
		for (Map.Entry<String, CustomSession> entry : tokensInMemory.entrySet()) {
			Date now = new Date(entry.getValue().getDate() + TimeUnit.MINUTES.toMillis(30));
			Date other = new Date(System.currentTimeMillis());
			if (now.before(other)) {
				tokensInMemory.remove(entry.getKey());
				System.out.println("deleting..");
				System.out.println(Arrays.asList(tokensInMemory));
			}
		}

	}

}
