<!DOCTYPE HTML>
<html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s"  			uri="http://www.connsec.com/tags" %>
<%@ page   import="com.connsec.web.*"%>
<head>
	<jsp:include page="common/header.jsp"/>
	<jsp:include page="common/common.css.jsp"/>
	
	<jsp:include page="common/common.js.jsp"/>
	<%
		Object sign_in_error_object=request.getSession().getAttribute(WebConstants.SIGN_IN_ERROR);
		Integer sign_in_error=sign_in_error_object==null?0:Integer.parseInt(sign_in_error_object.toString());
		request.getSession().removeAttribute(WebConstants.SIGN_IN_ERROR);
	 %>
	
	<script type="text/javascript"> 
	/**
	 *submit form
	**/		
	function doLoginSubmit(){
		$.cookie("username", $("#loginForm input[name=j_username]").val(), { expires: 7 });
		$("#loginForm").submit();
	};
	/**
	 * when press ENTER key,do form submit
	**/
	document.onkeydown=function(event){
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if(e && e.keyCode==13){ 
			doLoginSubmit();
		};
	};
	
	$(function(){
		/**
		 *submit loginForm
		**/	
		$("#loginSubmit").on("click",function(){
			doLoginSubmit();
		});
		
		$("input[name=j_username]").val($.cookie("username")==undefined?"":$.cookie("username"));
	});
	</script>
</head>
<body>
	<div id="top">
		<jsp:include page="common/nologintop.jsp"/>
	</div>
	<div id="content">
		<div class="container">	
			<table border="0">
				<tr>
					<td width="630px">
						
					</td>
					<td  width="358px">
						<form id="loginForm" name="loginForm"  action="<s:Base/>/login" method="post">
							<table   border=0 class="datatable" >
								<tr>
									<td colspan="2" style="background-color:#FBFBFB;"><s:Locale code="login.text.title"/></td>
								</tr>
								 <%if(sign_in_error>0){ %>
								<tr>
									<td colspan="2" style="color: #F30A0A;font-weight: bold;">
									<%if(sign_in_error==1){ %>
										<s:Locale code="login.text.error.user.null"/>
									<%} %>
									<%if(sign_in_error==2){ %>
										<s:Locale code="login.text.error.password.null"/>
									<%} %>
									<%if(sign_in_error==3){ %>
										<s:Locale code="login.text.error.user"/>
									<%} %>
									<%if(sign_in_error==4){ %>
										<s:Locale code="login.text.error.password"/>
									<%} %>
									</td>
								</tr>
								<%} %>
								<tr style="display: none;">
									<td >j_session</td>
									<td><input id="j_session" type="text" name="j_session" value="<%=request.getSession().getId()%>"/>
									</td>
								</tr>
								<tr>
									<td style="width: 80px;"><s:Locale code="login.text.username"/></td>
									<td><input id="j_username" type="text" name="j_username" />
									</td>
								</tr>
								<tr>
									<td><s:Locale code="login.text.password"/></td>
									<td><input id="j_password"  type="password" name="j_password" />
									</td>
								</tr>
								<tr>
									<td colspan="2" style="text-align: center;"><input  id="loginSubmit"  class="button login_button" type="button" name="loginSubmit"  value="<s:Locale code="login.text.submit"/>" />
									</td>
								</tr>
							</table>
					
						</form>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="footer">
		<jsp:include page="common/footer.jsp"/>
	</div>
</body>
</html>
