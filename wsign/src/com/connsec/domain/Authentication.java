package com.connsec.domain;

import javax.servlet.http.HttpServletRequest;

public  class Authentication {

	HttpServletRequest request;
	protected String principal;

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public Authentication(String principal) {
		super();
		this.principal = principal;
	}
	
	public Authentication(String principal,HttpServletRequest request) {
		super();
		this.request=request;
		this.principal = principal;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	
}
