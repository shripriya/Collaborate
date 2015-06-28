package com.slabs.collaborate.session.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slabs.collaborate.core.entity.User;

public class SessionFilter implements Filter {

	private static final Logger L = LoggerFactory.getLogger(SessionFilter.class);

	private ServletContext context;

	public void destroy() {

		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String uri = req.getRequestURI();
		this.context.log("Requested Resource::" + uri);

		HttpSession session = req.getSession(false);

		boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));

		if (session == null) {
			if (isAjax) {
				chain.doFilter(request, response);
			} else {
				L.info("Unauthorized access request");
				res.sendRedirect("/Collaborate");
			}
		} else if (session != null) {
			User user = (User) session.getAttribute("user_info");
			if (user != null) {
				chain.doFilter(request, response);
			} else {
				L.info("Unauthorized access request");
				session.invalidate();
				res.sendRedirect("/Collaborate");
			}
		}

	}

	public void init(FilterConfig filterConfig) throws ServletException {

		context = filterConfig.getServletContext();
		context.log("Collaborate Session Filter Initialized");

	}

}
