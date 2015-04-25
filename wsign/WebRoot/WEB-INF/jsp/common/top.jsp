<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s"  			uri="http://www.connsec.com/tags" %>
<%@ page   import="com.connsec.web.*"%>
<%@ page   import="com.connsec.domain.*"%>

<div id="topBar"  > 
	<div class="container">
		<div style="float:left;margin-left:20px;margin-top: 5px;"><IMG SRC="<s:Base/>/images/logo.jpg" style="width:55px;heigth:55px"></div>
		<div style="margin-top:15px;margin-left:10px;float:left">
			<div style="letter-spacing:2px;font-size:28px;font-weight:bolder;"><s:Locale code="global.application"/></div>
			
		</div>
		<div style="margin-top:25px;margin-right:10px;float:right;">
			<table style="height: 31px;">
				<tr>
					<td style="font-weight: bold;">
						<s:Locale code="global.text.welcome"/>：&nbsp;&nbsp;<%=((Authentication)request.getSession().getAttribute(WebConstants.AUTHENTICATION_TOKEN)).getPrincipal() %>&nbsp;&nbsp;
					</td>
					
					<td class="ui-widget-header" >
						<a  href="<s:Base/>/logout">
							<div  style="float:right;" >&nbsp;&nbsp;<s:Locale code="global.text.logout"/>&nbsp;&nbsp;</div>
						</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>