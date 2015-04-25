package com.connsec.domain;

import javax.servlet.http.HttpServletRequest;


public class UsernamePasswordAuthenticationToken  extends Authentication{
	
	String credentials;
	
	public UsernamePasswordAuthenticationToken(String principal) {
		super(principal);
		// TODO Auto-generated constructor stub
	}

	public UsernamePasswordAuthenticationToken(String principal,
			String credentials) {
		super(principal);
		this.credentials = credentials;
	}

	public UsernamePasswordAuthenticationToken(String principal,
			String credentials,HttpServletRequest request) {
		super(principal,request);
		this.credentials = credentials;
	}
	
	@Override
	public String getPrincipal() {
		return super.getPrincipal();
	}


	@Override
	public void setPrincipal(String principal) {
		super.setPrincipal(principal);
	}


	public String getCredentials() {
		return credentials;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	@Override
	public String toString() {
		return "UsernamePasswordAuthenticationToken [credentials="
				+ credentials + ", principal=" + principal + "]";
	}


}
