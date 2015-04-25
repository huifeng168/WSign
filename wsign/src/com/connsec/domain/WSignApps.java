package com.connsec.domain;

public class WSignApps {
	
	String client_id;
	String client_secret;
	String target;
	String method;
	Integer expires;
	boolean encrypt;
	String algorithm;
	boolean sign;
	
	public WSignApps() {
		
	}
	
	
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getClient_secret() {
		return client_secret;
	}
	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Integer getExpires() {
		return expires;
	}
	public void setExpires(Integer expires) {
		this.expires = expires;
	}
	public boolean isEncrypt() {
		return encrypt;
	}
	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}
	public String getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	public boolean isSign() {
		return sign;
	}
	public void setSign(boolean sign) {
		this.sign = sign;
	}
	
	@Override
	public String toString() {
		return "SignApps [client_id=" + client_id + ", client_secret="
				+ client_secret + ", target=" + target + ", method=" + method
				+ ", expires=" + expires + ", encrypt=" + encrypt
				+ ", algorithm=" + algorithm + ", sign=" + sign + "]";
	}
	
	
}
