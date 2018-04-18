<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Send</title>
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<%@ include file = "/WEB-INF/views/header.jsp" %>
</head>
<body>

	<form:form class="col-md-4 col-md-offset-4" method="post" modelAttribute="transfer">
		<form:errors path="*" />

		<label>Choose your address</label><br>
		<form:select path="sender" items="${accounts }" multiple="false" itemValue="address"/> <br><br>

		<label	>Recipient bitcoin address</label>
		<form:input class="form-control" path="reciver" />
		<font size="3" color="red">${param.message }</font> <br>

		<label>Ammount in BTC</label>
		<form:input type="number" step="0.00000001" class="form-control" path="ammount" />
		<font size="3" color="red">${param.message1 }</font> <br>

		<label>Describe</label>
		<form:input class="form-control" path="description" /> <br>

		<label>Select fee %</label>
		<form:input type="number" step="0.01" min="0.5" value="0.5" class="form-control" path="fee" />

		<button type="submit" class="btn btn-success center-block">Send</button>
	</form:form>

</body>
</html>