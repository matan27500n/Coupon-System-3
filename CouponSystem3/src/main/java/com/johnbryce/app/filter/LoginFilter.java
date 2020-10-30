package com.johnbryce.app.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.johnbryce.app.exceptions.NotExistException;
import com.johnbryce.app.security.TokenManager;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(2)
public class LoginFilter implements Filter {

	@Autowired
	private TokenManager tokenManager;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		String pageRequested = req.getRequestURL().toString();

		if (pageRequested.contains("/login")) {
			System.out.println(pageRequested);
			chain.doFilter(request, response);
			return;
		}

		if (pageRequested.contains("/register")) {
			System.out.println(pageRequested);
			chain.doFilter(request, response);
			return;
		}

		String token = req.getHeader("Authorization");
		System.out.println("Token : " + token);

		if (token != null) {
			try {
				tokenManager.isTokenExist(token);
				chain.doFilter(request, response);
				return;

			} catch (NotExistException e) {
				e.printStackTrace();
			}
		}

		HttpServletResponse res = (HttpServletResponse) response;
		res.setStatus(401);
	}
}