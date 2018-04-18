<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Recive</title>
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<%@ include file = "/WEB-INF/views/header.jsp" %>
</head>
<body>


	<form:form class="col-md-4 col-md-offset-4" method="post" modelAttribute="transfer">
		<form:errors path="*" />

		<label>Choose your address</label>
		<form:select path="reciver" items="${accounts }" itemValue="address"></form:select> <br>

		<label>Sender email</label>
		<input type="email" name="email" class="form-control">

		<label>Ammount in BTC</label>
		<form:input type="number" step="0.00000001" class="form-control" path="ammount" />

		<label>Describe</label>
		<form:input class="form-control" path="description" />

		<br><button type="submit" class="btn btn-success center-block">Send</button>
	</form:form>

</body>
</html>