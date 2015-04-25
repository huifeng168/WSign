<!DOCTYPE HTML>
<html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s"  			uri="http://www.connsec.com/tags" %>
<head>
	<jsp:include page="common/header.jsp"/>
	<jsp:include page="common/common.css.jsp"/>
	
	<jsp:include page="common/common.js.jsp"/>
	<script type="text/javascript"> 

	</script>
</head>
<body>
	<div id="top">
		<jsp:include page="common/top.jsp"/>
	</div>
	<div id="content">
		<div class="container">	
			<table class="datatable">
				<tr>
					<td><img src="<s:Base/>/images/simple.png"/></td>
					<td>Cookie Based</td>
					<td><a href="http://login.connsec.com:8080/wsign/login?target=http://login.connsec.com:8080/wc/index.jsp&wsign=wc" target="_blank">Visite</a></td>
				</tr>
				<tr>
					<td><img src="<s:Base/>/images/basic.png"/></td>
					<td>Token Based</td>
					<td><a href="http://login.connsec.com:8080/wsign/login?target=http://login.connsec.com:8080/wt/index.jsp&wsign=wt"  target="_blank">Visite</a></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="footer">
		<jsp:include page="common/footer.jsp"/>
	</div>
</body>
</html>

