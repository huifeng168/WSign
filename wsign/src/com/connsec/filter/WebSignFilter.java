package com.connsec.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import com.connsec.domain.SignTarget;
import com.connsec.web.WebConstants;

public class WebSignFilter implements Filter {

	final static Logger logger = Logger.getLogger(WebSignFilter.class);
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
        //HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        String language=request.getParameter("language");
        
        String acceptLanguages=request.getHeader("Accept-Language");
        String []browseLanguages=acceptLanguages.split(",");
        String browseLanguage=browseLanguages.length>0?browseLanguages[0]:acceptLanguages;
        
        logger.debug("accept Language :"+acceptLanguages);
        logger.debug("browse Language :"+browseLanguage);
        
        if(language==null&&request.getSession().getAttribute(WebConstants.LANGUAGE)==null){
        	request.getSession().setAttribute(WebConstants.LANGUAGE, browseLanguage);
        }
        
        if(language!=null){
        	String latestLanguage=(String)request.getSession().getAttribute(WebConstants.LANGUAGE);
        	if(!language.equals(latestLanguage)){
        		request.getSession().setAttribute(WebConstants.LANGUAGE, language);
        	}
        }
        
        logger.debug("current Language :"+request.getSession().getAttribute(WebConstants.LANGUAGE));
        
        SignTarget signTarget=new SignTarget(request);
        
        if(!signTarget.getTarget().equals("")){
        	SignTarget latestSignTarget=(SignTarget)request.getSession().getAttribute(WebConstants.SINGLE_SIGN_ON_TARGET);
        	
        	if(latestSignTarget==null||!signTarget.equals(latestSignTarget)){
        		request.getSession().setAttribute(WebConstants.SINGLE_SIGN_ON_TARGET, signTarget);
        		logger.debug("signTarget : "+signTarget);
        	}
        }
        
		filterChain.doFilter(servletRequest, servletResponse);
		
	}

	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	
}
