package com.connsec.web;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.connsec.authentication.AbstractAuthenticationProvider;
import com.connsec.domain.Authentication;
import com.connsec.domain.SignTarget;
import com.connsec.domain.UsernamePasswordAuthenticationToken;

public class Login extends HttpServlet {
	final static Logger logger = Logger.getLogger(Login.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 7294450246652770342L;

	/**
	 * Constructor of the object.
	 */
	public Login() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		
		SignTarget signTarget=(SignTarget)request.getSession().getAttribute(WebConstants.SINGLE_SIGN_ON_TARGET);
		
		if(request.getSession().getAttribute(WebConstants.AUTHENTICATION_TOKEN)!=null){
			 if(signTarget!=null&&signTarget.validated()){
	        	response.sendRedirect(request.getContextPath()+"/authorize/?"+signTarget.toParameter());
	         }else{
	        	request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
	         }
			return;
		}
		String username=request.getParameter("j_username");
		String password=request.getParameter("j_password");
		String j_session=request.getParameter("j_session");
		
		if(j_session==null||j_session.equals("")||!j_session.equals(request.getSession().getId())){
			logger.debug("Session Error .");
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}
		
		if(username==null||username.equals("")){
			if(request.getMethod().equalsIgnoreCase("POST")){
				request.getSession().setAttribute(WebConstants.SIGN_IN_ERROR, 1);
			}
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}
		
		if(password==null||password.equals("")){
			if(request.getMethod().equalsIgnoreCase("POST")){
				request.getSession().setAttribute(WebConstants.SIGN_IN_ERROR, 2);
			}
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}
		
		UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(username,password,request);
		
		Class<?> classAuthenticationProvider=null;
		Object objectAuthenticationProvider=null;
		try {
			classAuthenticationProvider = Class.forName(WebSignConfig.getInstance().get("config.authentication.provider"));
			Constructor<?> constructor = classAuthenticationProvider.getConstructor();
			objectAuthenticationProvider=constructor.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		
		AbstractAuthenticationProvider authenticationProvider =(AbstractAuthenticationProvider)objectAuthenticationProvider;
		Authentication authenticationToken=authenticationProvider.doAuthentication(authentication);
		
		if(authenticationToken!=null){
			request.getSession().setAttribute(WebConstants.AUTHENTICATION_TOKEN, authenticationToken);
	        //
			if(signTarget!=null&&signTarget.validated()){
	        	response.sendRedirect(request.getContextPath()+"/authorize/?"+signTarget.toParameter());
	        }else{
	        	request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
	        }
		}else{
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
