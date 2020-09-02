package com.johnbryce.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CouponSystem3Application {

	public static void main(String[] args) {
		System.out.println("START");
		SpringApplication.run(CouponSystem3Application.class, args);
		System.out.println("Ioc container is ready...");
	}

}
