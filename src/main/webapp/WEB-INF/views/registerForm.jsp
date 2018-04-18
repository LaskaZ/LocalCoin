<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register</title>
<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
</head>
<body>
	

	<form:form class="col-md-4 col-md-offset-4" modelAttribute="user" method="post">
		<h2>${param.message }</h2>
		<form:input path="userName" class="form-control" placeholder="Username" />

		<form:input path="email" class="form-control" placeholder="Email" />

		<form:password path="password" class="form-control" placeholder="Password" />

		<input type="password" name="passwordConfirm" class="form-control" placeholder="Confirm password">

		<a href="/user/account"><button type="submit" class="btn btn-primary center-block">Register</button></a>
	</form:form>


</body>
</html>