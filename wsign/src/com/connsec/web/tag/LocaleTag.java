package com.connsec.web.tag;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.connsec.web.WebConstants;


/**
 * @author Crystal.Sea
 *
 */
public class LocaleTag extends TagSupport{
	final static Logger logger = Logger.getLogger(LocaleTag.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -3906613920420893358L;
	
	private PageContext pageContext;
	
	private String code;
	
	

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}
	
	public final int doStartTag() throws JspException{
		return SKIP_BODY;
	}
	
	public final int doEndTag() throws JspException{		
		int tagReturn=EVAL_PAGE;
		try{

			Object languageObject=pageContext.getSession().getAttribute(WebConstants.LANGUAGE);
			String language="";
			Locale currentLocale ;
			if(languageObject==null){
				currentLocale=pageContext.getRequest().getLocale();
			}else{
				language=pageContext.getSession().getAttribute(WebConstants.LANGUAGE).toString();
				String [] languageCountry=language.split("-");
				
				if(languageCountry.length>1){
					currentLocale = new Locale(languageCountry[0], languageCountry[1]);
				}else{
					currentLocale = new Locale(language);
				}
			}
			
			if(null==code){
				pageContext.getOut().print(language);
			}else{
				logger.debug("currentLocale : "+currentLocale);
				ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.MessageBundle",currentLocale);
				String localeText=resourceBundle.getString(code);
				logger.debug("locale Text : "+localeText);
				pageContext.getOut().print(localeText);
			}
			
			pageContext.getOut().flush();
		} catch (IOException e){
			throw new JspException("exception="+e.getMessage());
		}
		return tagReturn;
		
	}

}
