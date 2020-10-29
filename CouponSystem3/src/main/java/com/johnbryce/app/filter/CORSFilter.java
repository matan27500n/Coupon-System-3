package com.johnbryce.app.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class CORSFilter implements Filter {

	public CORSFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;

		((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods",
				"GET, OPTIONS, HEAD, PUT, POST, DELETE");
		((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers",
				"authorization, Origin, Accept, x-auth-token, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");

		if (request.getMethod().equals("OPTIONS")) {
			return;
		}

		chain.doFilter(request, servletResponse);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}