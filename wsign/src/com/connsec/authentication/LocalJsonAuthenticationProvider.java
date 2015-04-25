package com.connsec.authentication;

import org.apache.log4j.Logger;

import com.connsec.domain.Authentication;
import com.connsec.domain.UserInfo;
import com.connsec.domain.UsernamePasswordAuthenticationToken;
import com.connsec.web.WebConstants;
import com.connsec.web.WebSignConfig;

public class LocalJsonAuthenticationProvider extends AbstractAuthenticationProvider{
	final static Logger logger = Logger.getLogger(LocalJsonAuthenticationProvider.class);
	
	@Override
	public Authentication doAuthentication(Authentication authentication) {
		
		UsernamePasswordAuthenticationToken authenticationToken=(UsernamePasswordAuthenticationToken)authentication;
		
		UserInfo userInfo=WebSignConfig.getInstance().getWSignUserInfosConfig().get(authenticationToken.getPrincipal());
		
		//no find user
		if(userInfo==null){
			authentication.getRequest().getSession().setAttribute(WebConstants.SIGN_IN_ERROR, 3);
			return null;
		}
		
		//password error
		if(!userInfo.getCredential().equals(authenticationToken.getCredentials())){
			authentication.getRequest().getSession().setAttribute(WebConstants.SIGN_IN_ERROR, 4);
			return null;
		}
		
		//login success
		return new UsernamePasswordAuthenticationToken(authenticationToken.getPrincipal(),authenticationToken.getCredentials());
		
	}

}
