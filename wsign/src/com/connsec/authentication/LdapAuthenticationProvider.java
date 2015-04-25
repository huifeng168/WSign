package com.connsec.authentication;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.log4j.Logger;

import com.connsec.domain.Authentication;
import com.connsec.domain.UsernamePasswordAuthenticationToken;
import com.connsec.util.LdapUtils;
import com.connsec.web.WebConstants;
import com.connsec.web.WebSignConfig;

public class LdapAuthenticationProvider extends AbstractAuthenticationProvider{

	final static Logger logger = Logger.getLogger(LdapAuthenticationProvider.class);
	
	
	@Override
	public Authentication doAuthentication(Authentication authentication) {
		
		UsernamePasswordAuthenticationToken authenticationToken=(UsernamePasswordAuthenticationToken)authentication;
		
		LdapUtils ldapUtils=new LdapUtils(
				WebSignConfig.getInstance().get("config.ldap.providerUrl"),
				WebSignConfig.getInstance().get("config.ldap.principal"),
				WebSignConfig.getInstance().get("config.ldap.credentials"),
				WebSignConfig.getInstance().get("config.ldap.baseDN")
				);
		
		String queryFilter = "("+WebSignConfig.getInstance().get("config.ldap.filterAttribute")+"="+authenticationToken.getPrincipal()+")";
		String dn="";
		SearchControls constraints = new SearchControls();
		constraints.setSearchScope(ldapUtils.getSearchScope());
		try {
			NamingEnumeration<SearchResult> results = ldapUtils.getConnection()
					.search(ldapUtils.getBaseDN(), queryFilter, constraints);
			
			if (results == null || !results.hasMore()) {
				logger.error("Ldap user "+authenticationToken.getPrincipal() +" not found . ");
				authentication.getRequest().getSession().setAttribute(WebConstants.SIGN_IN_ERROR, 3);
				return null;
			}else{
				while (results != null && results.hasMore()) {
					SearchResult sr = (SearchResult) results.next();
					//String rdn = sr.getName();
					dn = sr.getNameInNamespace();
					logger.debug("Directory user dn is "+dn+" .");
				}
			}
		} catch (NamingException e) {
			logger.error("query throw NamingException:" + e.getMessage());
		} finally {
			ldapUtils.close();
		}
		
		LdapUtils ldapPassWordValid=new LdapUtils(ldapUtils.getProviderUrl(),dn,authenticationToken.getCredentials());
		ldapPassWordValid.openConnection();
		if(ldapPassWordValid.getCtx()==null){
			logger.debug("Directory user " + authenticationToken.getPrincipal() + "  is validate .");
			ldapPassWordValid.close();
			authentication.getRequest().getSession().setAttribute(WebConstants.SIGN_IN_ERROR, 4);
			return null;
		}
		return  new UsernamePasswordAuthenticationToken(authenticationToken.getPrincipal(),authenticationToken.getCredentials());
	}

}
