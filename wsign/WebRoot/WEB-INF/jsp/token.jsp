<!DOCTYPE HTML>
<html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
	<jsp:include page="common/header.jsp"/>
	<jsp:include page="common/common.css.jsp"/>
	
	<jsp:include page="common/common.js.jsp"/>
	<script type="text/javascript"> 

	</script>
</head>
<body  onload="document.forms[0].submit()"  style="display:none">
	<div id="top">
		<jsp:include page="common/nologintop.jsp"/>
	</div>
	<div id="content">
		<div class="container">	
			<form action="${target}" method="post">
				<input type="hidden" name="WSign_Token" value="${WSign_Token}"/><br>
				<input type="hidden" name="relaystate" value="${relaystate}"/><br>
				<input type="submit" name="loginSubmit" value="Click Button To Continue..." class="button">
			</form>
		</div>
	</div>
	<div id="footer">
		<jsp:include page="common/footer.jsp"/>
	</div>
</body>
</html>