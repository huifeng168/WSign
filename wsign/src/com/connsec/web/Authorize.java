package com.connsec.web;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.connsec.crypto.Base64Utils;
import com.connsec.crypto.ReciprocalUtils;
import com.connsec.domain.Authentication;
import com.connsec.domain.SignTarget;
import com.connsec.domain.WSignApps;
import com.connsec.util.DateUtil;
import com.connsec.util.PathUtils;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.Base64URL;

public class Authorize extends HttpServlet {

	final static Logger logger = Logger.getLogger(Authorize.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 7441712973744322853L;

	public static class SIGNTYPE {

		public static String COOKIE_BASED 	= 	"wc";

		public static String TOKEN_BASED 	= 	"wt";

	}

	/**
	 * Constructor of the object.
	 */
	public Authorize() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		SignTarget signTarget = new SignTarget(request);

		if (signTarget.getTarget() == null) {
			signTarget = (SignTarget) request.getSession().getAttribute(WebConstants.SINGLE_SIGN_ON_TARGET);
		}
		request.getSession().removeAttribute(WebConstants.SINGLE_SIGN_ON_TARGET);
		
		logger.debug("signTarget : " + signTarget);

		if (!signTarget.validated()||WebSignConfig.getInstance().getWSignAppsConfig().get(signTarget.getTarget())==null) {
			request.setAttribute("signTarget", signTarget);
			logger.debug("WSignAppsConfig for "+signTarget.getTarget()+" : " + WebSignConfig.getInstance().getWSignAppsConfig().get(signTarget.getTarget()));
			request.getRequestDispatcher("/WEB-INF/jsp/notarget.jsp").forward(request, response);
			return;
		}
		
		logger.debug("WSignAppsConfig for "+signTarget.getTarget()+" : " + WebSignConfig.getInstance().getWSignAppsConfig().get(signTarget.getTarget()));
		
		if (signTarget.getWsign().equals(SIGNTYPE.COOKIE_BASED)) {
			doCookieBasedAuthorize(request, response,signTarget);
		} else {
			doTokenBasedAuthorize(request, response,signTarget);
			request.getRequestDispatcher("/WEB-INF/jsp/token.jsp").forward(request, response);
		}

	}

	public void doCookieBasedAuthorize(HttpServletRequest request,
			HttpServletResponse response,SignTarget signTarget) throws ServletException, IOException {
		
			String wsign_token=buildToken(request,signTarget);
			
			Cookie cookie= new Cookie(WebConstants.WSIGN_TOKEN,wsign_token);
			
			Integer maxAge=WebSignConfig.getInstance().getWSignAppsConfig().get(signTarget.getTarget()).getExpires()*60;
			logger.debug("Cookie Max Age :"+maxAge+" seconds.");
			cookie.setMaxAge(maxAge);
			
			cookie.setPath("/");
			cookie.setDomain("."+WebSignConfig.getInstance().get("config.base.domain"));
			
			logger.debug("Sub Domain Name : "+"."+WebSignConfig.getInstance().get("config.base.domain"));
			response.addCookie(cookie);
			response.sendRedirect(signTarget.getTarget());
	}

	public void doTokenBasedAuthorize(HttpServletRequest request,
			HttpServletResponse response,SignTarget signTarget) throws ServletException, IOException {
			String wsign_token=buildToken(request,signTarget);
			request.setAttribute(WebConstants.SINGLE_SIGN_ON_TARGET,signTarget.getTarget());
			request.setAttribute(WebConstants.SINGLE_SIGN_ON_RELAYSTATE,signTarget.getRelayState());
			request.setAttribute(WebConstants.WSIGN_TOKEN,wsign_token);
	}
	
	public String buildToken(HttpServletRequest request,SignTarget signTarget){
		Authentication authentication=(Authentication)request.getSession().getAttribute(WebConstants.AUTHENTICATION_TOKEN);
		
		WSignApps wsignApps=WebSignConfig.getInstance().getWSignAppsConfig().get(signTarget.getTarget());
		DateTime currentDateTime=new DateTime();
		currentDateTime=currentDateTime.plusMinutes(wsignApps.getExpires());
		
		String wsign_token=authentication.getPrincipal()+"@@"+DateUtil.toUtc(currentDateTime);
		
		logger.debug("wsign_token " +wsign_token);
		if(wsignApps.isEncrypt()){
			logger.debug("encrypt token ");
			wsign_token=encrypt(wsign_token,wsignApps);
			logger.debug("after encrypt wsign_token " +wsign_token);
		}
		
		wsign_token=Base64Utils.base64UrlEncode(wsign_token.getBytes());
		
		String wsign_signature="";
		if(wsignApps.isSign()){
			logger.debug("sign token ");
			wsign_signature=sign(wsign_token);
			logger.debug("after sign wsign_signature " +wsign_signature);
		}
		
		wsign_token=wsign_token+"."+wsign_signature;
		
		logger.debug("final wsign_token " +wsign_token);
		
		return wsign_token;
	}

	
	
	public String encrypt(String unEncryptToken,WSignApps wsignApps){
		String encryptedToken=ReciprocalUtils.encode2Hex(unEncryptToken, wsignApps.getClient_secret(), wsignApps.getAlgorithm());
		return encryptedToken;
	}
	
	
	public String sign(String unSignToken){
		File jwksFile=new File(PathUtils.getInstance().getClassPath()+"keystore.jwks");
		try {
			JWKSet jwkSet=JWKSet.load(jwksFile);
			RSASSASigner  rsaSSASigner=new RSASSASigner(((RSAKey) jwkSet.getKeyByKeyId("connsec_rsa1")).toRSAPrivateKey());
			// sign it with the server's key
			Base64URL  base64URL =rsaSSASigner.sign(new JWSHeader(JWSAlgorithm.RS256), unSignToken.getBytes());
			
			logger.debug("Base64URL : "+base64URL);
			return base64URL.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (JOSEException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
