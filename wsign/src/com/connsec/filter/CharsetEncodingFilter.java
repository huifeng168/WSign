package com.connsec.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import com.connsec.web.WebSignConfig;

public class CharsetEncodingFilter implements Filter {
	final static Logger logger = Logger.getLogger(CharsetEncodingFilter.class);
	private String encoding;

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		servletRequest.setCharacterEncoding(encoding);
		filterChain.doFilter(servletRequest, servletResponse);
		
	}

	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		encoding=WebSignConfig.getInstance().get("config.characterencoding.encoding");
		logger.debug("Set Encoding : "+encoding);
	}
	
	
}
