package com.connsec.util;


import java.util.Properties;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;

import org.apache.log4j.Logger;

/**
 * @author Crystal
 *
 */
public class ActiveDirectoryUtils extends LdapUtils {
	final static Logger logger = Logger.getLogger(ActiveDirectoryUtils.class);
	
	protected String domain;

	/**
	 * 
	 */
	public ActiveDirectoryUtils() {
		super();
	}
	
	public ActiveDirectoryUtils(String providerUrl,String principal,String credentials,String baseDN,String domain) {
		this.providerUrl=providerUrl;
		this.principal=principal;
		this.credentials=credentials;
		this.searchScope=SearchControls.SUBTREE_SCOPE;
		this.baseDN=baseDN;
		this.domain=domain.toUpperCase();
	}
	
	public ActiveDirectoryUtils(String providerUrl,String principal,String credentials,String domain) {
		this.providerUrl=providerUrl;
		this.principal=principal;
		this.credentials=credentials;
		this.searchScope=SearchControls.SUBTREE_SCOPE;
		this.domain=domain.toUpperCase();
	}
	
	public ActiveDirectoryUtils(DirContext dirContext) {
		this.ctx=dirContext;
	}
	
	//connect to ldap server
	@Override
	public DirContext openConnection(){	     
		logger.info("PROVIDER_URL:"+providerUrl);
		logger.info("SECURITY_PRINCIPAL:"+principal);
		logger.info("SECURITY_CREDENTIALS:"+credentials);
		//LDAP
		Properties props = new Properties();
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		props.setProperty(Context.URL_PKG_PREFIXES, "com.sun.jndi.url");
		props.setProperty(Context.REFERRAL, referral);
		props.setProperty(Context.SECURITY_AUTHENTICATION, "simple");
		
		props.setProperty(Context.PROVIDER_URL, providerUrl);
		if(domain.indexOf(".")>-1){
			domain=domain.substring(0, domain.indexOf("."));
		}
		logger.info("PROVIDER_DOMAIN:"+domain);
		String activeDirectoryPrincipal=domain+"\\"+principal;
		logger.debug("Active Directory SECURITY_PRINCIPAL : "+activeDirectoryPrincipal);
		props.setProperty(Context.SECURITY_PRINCIPAL,activeDirectoryPrincipal);
		props.setProperty(Context.SECURITY_CREDENTIALS, credentials);
		
		if(ssl){
			if(System.getProperty("javax.net.ssl.trustStore")==null){
				System.setProperty("javax.net.ssl.trustStore", trustStore);
				System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
			}
			props.put(Context.SECURITY_PROTOCOL, "ssl");  
			props.put(Context.REFERRAL, "follow"); 
		}
		
		return InitialDirContext(props);
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain.toUpperCase();
	}
	

	

}
