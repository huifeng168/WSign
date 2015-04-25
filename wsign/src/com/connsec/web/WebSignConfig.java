package com.connsec.web;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.connsec.domain.LocalJsonUserInfoList;
import com.connsec.domain.UserInfo;
import com.connsec.domain.WSignApps;
import com.connsec.domain.WSignAppsList;
import com.connsec.util.JsonUtils;
import com.connsec.util.PathUtils;

public class WebSignConfig {
	final static Logger logger = Logger.getLogger(WebSignConfig.class);
	static Properties properties = new Properties();
	
	HashMap<String,WSignApps> WSignAppsConfig;
	
	HashMap<String,UserInfo> WSignUserInfosConfig;
	
	private static WebSignConfig instance;
	
	public static synchronized WebSignConfig getInstance() {
		if (instance == null) {
			
			instance = new WebSignConfig();
			
			loadwsigns();
			
			loadApps();
			
			loadJsonUserInfos();
		}
		return instance;
	}
	
	public static void loadwsigns(){
		InputStream in;
		try {
			logger.debug("wsign.properties : "+PathUtils.getInstance().getClassPath()+"wsign.properties");
			in = new BufferedInputStream (new FileInputStream((PathUtils.getInstance().getClassPath()+"wsign.properties")));
			properties.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	
	public static void loadApps(){
		try {
			
			InputStream wsignappin =  new BufferedInputStream (new FileInputStream((PathUtils.getInstance().getClassPath()+"wsignapps.json")));
			
	        byte[] data = new byte[1024];
	        int len = 0;
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        
	        while ((len = wsignappin.read(data)) != -1) {
	        	    outputStream.write(data, 0, len);
	        }
	        
	       wsignappin.close();
	       
	       String jsonString = new String(outputStream.toByteArray());
	       
	       
	       
	       WSignAppsList wsignAppsList=JsonUtils.gson2Object(jsonString, WSignAppsList.class);
	       getInstance().WSignAppsConfig=new HashMap<String,WSignApps>();
	       for(WSignApps wsignApps : wsignAppsList.getWSignApps()){
	    	   getInstance().WSignAppsConfig.put(wsignApps.getTarget(), wsignApps);
	       }
	       		   
	       logger.debug("WSignAppsList : \n"+jsonString);
	       
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadJsonUserInfos(){
		try {
				
			InputStream wsignuserin =  new BufferedInputStream (new FileInputStream((PathUtils.getInstance().getClassPath()+"users.json")));
			
	        byte[] data = new byte[1024];
	        int len = 0;
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        
	        while ((len = wsignuserin.read(data)) != -1) {
	        	    outputStream.write(data, 0, len);
	        }
	        
	        wsignuserin.close();
	       
	       String jsonString = new String(outputStream.toByteArray());
	       
	       
	       
	       LocalJsonUserInfoList localJsonUserInfoList=JsonUtils.gson2Object(jsonString, LocalJsonUserInfoList.class);
	       
	       getInstance().WSignUserInfosConfig=new HashMap<String,UserInfo>();
	       
	       for(UserInfo userInfo : localJsonUserInfoList.getLocalJsonUserInfo()){
	    	   getInstance().WSignUserInfosConfig.put(userInfo.getUsername(), userInfo);
	       }
	       		  
	       logger.debug("LocalJsonUserInfoList : \n"+jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<String, WSignApps> getWSignAppsConfig() {
		return WSignAppsConfig;
	}

	public HashMap<String, UserInfo> getWSignUserInfosConfig() {
		return WSignUserInfosConfig;
	}

	public String get(String key){
		return properties.getProperty(key, "");
	}

}
