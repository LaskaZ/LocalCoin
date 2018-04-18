<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Homepage</title>
<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
</head>

<body>
	<div class="col-md-4 col-md-offset-7">
		<a href="/CryptocurrencyWallet/user/logout"><button type="submit">Logout</button></a>
	</div>

	<div class="col-md-4 col-md-offset-7">
		<div>Total balance in BTC: ${ammountInBTC }</div>
		<div>Total balance in USD: ${ammountInUSD }</div>
	</div>

	<div class="col-md-3 col-md-offset-3">
		<a href="account/send"><button type="submit" class="btn btn-primary">Send</button></a> 
		<a href="account/recive"><button type="submit" class="btn btn-primary">Recive</button></a> 
		<a href="account/addAddress"><button type="submit" class="btn btn-primary">New address</button></a>
	</div>

	<div class="col-md-8 col-md-offset-2">
		<table class="table">
			<tr>
				<th>Sender</th>
				<th>Reciver</th>
				<th>Ammount</th>
				<th>Date</th>
				<th>Description</th>
			</tr>

			<c:forEach items="${transfers }" var="transfer">
				<tr class="${transfer.reciver.user.id == user.id ? 'alert alert-success' : 'alert alert-danger'}">
					<td>${transfer.sender.address }</td>
					<td>${transfer.reciver.address }</td>
					<td>${transfer.ammount }</td>
					<td>${transfer.created }</td>
					<td>${transfer.description }</td>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>