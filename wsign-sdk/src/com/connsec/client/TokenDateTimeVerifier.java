package com.connsec.client;

import org.joda.time.DateTime;

import com.connsec.util.DateUtil;

public class TokenDateTimeVerifier {

	public static boolean verifier(String utcDateTime) {
		DateTime verifierDateTime=DateUtil.toUtcDate(utcDateTime);
		if(verifierDateTime.isAfterNow()){
			return true;
		}else{
			return false;
		}
	}
	
}
