package com.johnbryce.app.schedule;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.johnbryce.app.security.CustomSession;
import lombok.Getter;

@Component
@Getter
public class TokenExpiredJob {

	@Autowired
	private Map<String, CustomSession> tokensInMemory;

	@Scheduled(fixedRate = 1000 * 60 * 10)
	public void removeExpiredCoupons() {
		Iterator<Entry<String, CustomSession>> iterator = tokensInMemory.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, CustomSession> entry = iterator.next();
			Date now = new Date(entry.getValue().getDate() + TimeUnit.MINUTES.toMillis(30));
			Date other = new Date(System.currentTimeMillis());
			if (now.before(other)) {
				iterator.remove();
				System.out.println("deleting...");
			}
			System.out.println(tokensInMemory.entrySet());
		}
	}

}
