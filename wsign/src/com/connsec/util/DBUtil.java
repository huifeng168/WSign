package com.connsec.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DBUtil {

	 private Connection connection = null; 
	 private String url = "jdbc:mysql://localhost/wsign?autoReconnect=true&characterEncoding=UTF-8";
	 String driverclass="com.mysql.jdbc.Driver";
	 String user="root";
	 String password="connsec";
	 
	 public DBUtil(String url, String user, String password, String driverclass) {
		super();
		this.url = url;
		this.user = user;
		this.password = password;
		this.driverclass = driverclass;
	}
	 
	/**
	  * 创建连接
	  * @return
	  */
	 public Connection createConnection(){  
	  try {
		 Class.forName(driverclass);
	   connection = DriverManager.getConnection(url, user, password);
	   return connection;   
	  } catch (ClassNotFoundException e) {
	   Logger.getLogger(this.getClass()).error(e.getMessage());
	   return null;
	  } catch (SQLException e) {
	   Logger.getLogger(this.getClass()).error(e.getMessage());
	   return null;
	  }
	 } 
	 /**
	  * 释放连接
	  */
	 public void releaseConnection(){  
	  if (connection!=null)
	   try {
	    connection.close();
	   } catch (SQLException e) {
	    Logger.getLogger(this.getClass()).error(e.getMessage());
	   }
	 }
}
