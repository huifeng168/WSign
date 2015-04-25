package com.connsec.client;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieTokenReadUtil {
	 public static String read(HttpServletRequest request)
	  {
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	      for (int i = 0; i < cookies.length; i++) {
	        if (cookies[i].getName().equalsIgnoreCase("WSign_Token")) {
	          return cookies[i].getValue();
	        }
	      }
	    }
	    return null;
	  }
}
