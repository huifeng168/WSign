<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page   import="java.io.*"%>
<%@ page   import="com.nimbusds.jose.*"%>
<%@ page   import="com.nimbusds.jose.crypto.*"%>
<%@ page   import="com.nimbusds.jose.jwk.*"%>
<%@ page   import="com.connsec.util.*"%>
<%@ page   import="com.connsec.crypto.*"%>
<%@ page   import="com.nimbusds.jose.util.*"%>
<%@ page   import="com.connsec.client.*"%>


<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	boolean isVerifier=false;
		
	String WSign_Token_String=CookieTokenReadUtil.read(request);
	WSignToken WSignToken=new WSignToken(WSign_Token_String);
	
	Base64URL base64URL=new Base64URL(WSignToken.getWsign_signature());
	isVerifier=(new TokenSignatureVerifier()).verifier(WSignToken.getWsign_token(), base64URL);// WSign_Token_Sign[0].getBytes(), base64URL);
	System.out.println("verify : "+isVerifier);
	
	
	WSignToken.decode("KOHaEcb8","DES");
	System.out.println("decodeToken : "+WSignToken.getWsign_token());
	
	
	WSignToken.parse();
	System.out.println("WSignToken : "+WSignToken);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Web Sign Cookie Demo</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="Web Sign Cookie Demo">
	<link rel="shortcut icon" type="image/x-icon" href="<%=basePath %>/images/favicon.ico"/>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<style type="text/css">
		body{
			margin: 0;
			margin-top: 0px;
			margin-left: auto;
			margin-right: auto;
			padding: 0 0 0 0px;
			font-size: 12px;
			text-align:center;
			float:center;
			font-family: "Arial", "Helvetica", "Verdana", "sans-serif";
		}
		.container {
			width: 990px;
			margin-left: auto;
			margin-right: auto;
			padding: 0 10px
		}
		table.datatable {
			border: 1px solid #d8dcdf;
			border-collapse:collapse;
			border-spacing:0;
			width: 100%;
		}
		
		table.datatable th{
			border: 1px solid #d8dcdf;
			border-collapse:collapse;
			border-spacing:0;
			height: 40px;
		}
		
		
		table.datatable td{
			border: 1px solid #d8dcdf;
			border-collapse:collapse;
			border-spacing:0;
			height: 40px;
		}
		
		table.datatable td.title{
			text-align: center;
			font-size: 20px;
			font-weight: bold;
		}
	</style>
  </head>
  
  <body>
    	<div class="container">
	  		<table class="datatable">
	  			<tr>
	  				<td colspan="2" class="title">Web Sign Cookie Demo</td>
	  			</tr>
	    		<tr><td>username</td><td><%=WSignToken.getUsername() %></td></tr>
	    		<tr><td>authnat</td><td><%=WSignToken.getAuthnAt() %></td></tr>
	    		<tr><td>time Verifier</td><td><%=TokenDateTimeVerifier.verifier(WSignToken.getAuthnAt()) %></td></tr>
	    		<tr><td>signature Verifier</td><td><%=isVerifier %></td></tr>
	    	</table>
	    </div>
  </body>
</html>
