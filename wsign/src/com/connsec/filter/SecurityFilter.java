package com.connsec.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.connsec.web.WebConstants;

public class SecurityFilter implements Filter {

	final static Logger logger = Logger.getLogger(SecurityFilter.class);
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        if(request.getSession().getAttribute(WebConstants.AUTHENTICATION_TOKEN)==null){
        	request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        	return;
        }
        
		filterChain.doFilter(servletRequest, servletResponse);
		
	}

	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	
}
