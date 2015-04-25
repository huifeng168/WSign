package com.connsec.domain;

public class UserInfo {

	String username;
	String credential;
	
	public UserInfo(String username, String credential) {
		super();
		this.username = username;
		this.credential = credential;
	}


	public String getCredential() {
		return credential;
	}
	public void setCredential(String credential) {
		this.credential = credential;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}


	@Override
	public String toString() {
		return "UserInfo [username=" + username + ", password=" + credential
				+ "]";
	}
	
	
}
