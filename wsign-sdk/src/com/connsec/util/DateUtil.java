package com.connsec.util;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;



public class DateUtil {

	public final static String FORMAT_DATE_YYYY_MM_DD = "yyyy-MM-dd";
	
	
	public final static String FORMAT_DATE_ISO_TIMESTAMP="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	public final static String FORMAT_DATE_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
	public final static String FORMAT_DATE_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public final static String FORMAT_DATE_HH_MM_SS = "HH:mm:ss";

	/**
	 * Returns current system date.
	 * 
	 * @return current system date.
	 */
	public final static Date getCurrentDate() {
		return new Date();
	}


	public static String toUtc(java.util.Date date){
		DateTime datetime=new DateTime(date, ISOChronology.getInstanceUTC());
		return datetime.toString();
	}
	
	public static String toUtc(DateTime dateTime){
		return dateTime.toString();
	}
	
	public static String toUtc(String date){
		DateTime datetime=new DateTime(date, ISOChronology.getInstanceUTC());
		return datetime.toString();
	}

	public static DateTime toUtcDate(String date){
		DateTime datetime=new DateTime(date, ISOChronology.getInstanceUTC());
		return datetime;
	}
	
	public static String toUtcLocal(java.util.Date date){
		DateTime datetime=new DateTime(date,ISOChronology.getInstance());
		return datetime.toString();
	}
	
	public static String toUtcLocal(String date){
		DateTime datetime=new DateTime(date,ISOChronology.getInstance());
		return datetime.toString();
	}

}

