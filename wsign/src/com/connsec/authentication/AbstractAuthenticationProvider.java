package com.connsec.authentication;

import com.connsec.domain.Authentication;

public  abstract class AbstractAuthenticationProvider  {

	 public abstract Authentication doAuthentication(Authentication authentication );
}
