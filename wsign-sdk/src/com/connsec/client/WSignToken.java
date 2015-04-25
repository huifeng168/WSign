package com.connsec.client;

import com.connsec.crypto.Base64Utils;
import com.connsec.crypto.ReciprocalUtils;

public class WSignToken {
	
	String wsign_token;
	String wsign_signature;
	String username;
	String authnAt;
	private boolean isDecodeToken;
	
	public WSignToken(String wsign_token_signature) {
		String []WSign_Token_Sign=wsign_token_signature.split("\\.");
		this.wsign_token =WSign_Token_Sign[0];
		if(WSign_Token_Sign.length==2){
			this.wsign_signature = WSign_Token_Sign[1];
		}
	}
	
	public WSignToken(String wsign_token, String wsign_signature) {
		this.wsign_token = wsign_token;
		this.wsign_signature = wsign_signature;
	}
	
	public String getWsign_token() {
		return wsign_token;
	}
	public void setWsign_token(String wsign_token) {
		this.wsign_token = wsign_token;
	}
	public String getWsign_signature() {
		return wsign_signature;
	}
	
	public void setWsign_signature(String wsign_signature) {
		this.wsign_signature = wsign_signature;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthnAt() {
		return authnAt;
	}

	public void setAuthnAt(String authnAt) {
		this.authnAt = authnAt;
	}

	private  void decodeBase64Url(){
		if(!isDecodeToken){
			wsign_token=new String(Base64Utils.base64UrlDecode( wsign_token));
			isDecodeToken=true;
		}
	}
	
	public String decode(String secretKey,String algorithm){
		decodeBase64Url();
		wsign_token=ReciprocalUtils.decoderHex(wsign_token, secretKey,algorithm);
		return wsign_token;
	}
	
	public void parse(){
		decodeBase64Url();
		String [] splitToken=wsign_token.split("@@");
		if(splitToken.length==2){
			username=splitToken[0];
			authnAt=splitToken[1];
		}
	}

	@Override
	public String toString() {
		return "WSignToken [wsign_token=" + wsign_token + ", wsign_signature="
				+ wsign_signature + ", username=" + username + ", authnAt="
				+ authnAt + "]";
	}
	

	
}
