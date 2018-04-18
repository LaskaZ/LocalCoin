<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
</head>
<body>
	<form method="post" action="user/login"
		class="col-md-4 col-md-offset-4">
		<h3>${param.message }</h3>
		
		<input type="text" class="form-control" name="userName" placeholder="UserName/Email"> 
		<input type="password" name="password" class="form-control" placeholder="Password">

		<button type="submit" class="btn btn-primary center-block">Login</button>
		<br> <a href="user/register">You don't have an account? Click to register</a>
	</form>

</body>
</html>