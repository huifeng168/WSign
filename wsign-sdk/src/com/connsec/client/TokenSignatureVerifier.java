package com.connsec.client;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import com.connsec.util.PathUtils;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.Base64URL;

public class TokenSignatureVerifier {

	JWKSet jwkSet;

	public TokenSignatureVerifier() {
		try {
			File jwksFile = new File(PathUtils.getInstance().getClassPath()+ "jwk");
			jwkSet=JWKSet.load(jwksFile);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public TokenSignatureVerifier(String location) {
		try {
			if(location.startsWith("http")){
				jwkSet=JWKSet.load(new URL(location));
			}else{
				jwkSet=JWKSet.load(new File(location));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public boolean verifier(String WSign_Token,Base64URL wsign_signature){
		boolean isVerifier=false;
		try {
			RSASSAVerifier rsaSSAVerifier = new RSASSAVerifier(((RSAKey) jwkSet.getKeyByKeyId("connsec_rsa")).toRSAPublicKey());
			isVerifier = rsaSSAVerifier.verify(new JWSHeader(JWSAlgorithm.RS256), WSign_Token.getBytes(), wsign_signature);
		} catch (JOSEException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		System.out.println("verify : "+isVerifier);
		return isVerifier;
	}
}
