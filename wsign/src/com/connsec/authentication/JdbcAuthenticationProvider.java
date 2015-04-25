package com.connsec.authentication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.connsec.domain.Authentication;
import com.connsec.domain.UserInfo;
import com.connsec.domain.UsernamePasswordAuthenticationToken;
import com.connsec.util.DBUtil;
import com.connsec.web.WebConstants;
import com.connsec.web.WebSignConfig;

public class JdbcAuthenticationProvider extends AbstractAuthenticationProvider{
	final static Logger logger = Logger.getLogger(JdbcAuthenticationProvider.class);
	
	@Override
	public Authentication doAuthentication(Authentication authentication) {
		UsernamePasswordAuthenticationToken authenticationToken=(UsernamePasswordAuthenticationToken)authentication;
		UserInfo userInfo=null;
		try {
			DBUtil dbUtil=new DBUtil(
					WebSignConfig.getInstance().get("config.datasource.url"),
					WebSignConfig.getInstance().get("config.datasource.username"),
					WebSignConfig.getInstance().get("config.datasource.password"),
					WebSignConfig.getInstance().get("config.datasource.driverclass"));
			Connection  conn =dbUtil.createConnection();
			String queryUserInfoSql=WebSignConfig.getInstance().get("config.query.userinfo.sql");
			PreparedStatement  pstmt=conn.prepareStatement(queryUserInfoSql);
			pstmt.setString(1, authenticationToken.getPrincipal());
			
			logger.debug("Query UserInfo Sql : "+queryUserInfoSql);
			logger.debug("Parameter : "+authenticationToken.getPrincipal());
			
			ResultSet   resultSet =pstmt.executeQuery();
			
			while(resultSet.next()){
				userInfo =new UserInfo(resultSet.getString(1),resultSet.getString(2));
			}
			 
			dbUtil.releaseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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
