<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.ArrayList"%>
<html>
<head>
<meta charset="UTF-8">
<title>Registration</title>
<link rel="stylesheet" type="text/css" href="/AhmadiCIS4665/css/login.css">
</head>
<body  id="bodyreg">
<%ArrayList<String> lista = (ArrayList<String>)request.getAttribute("list"); %>
<div class="wrapper">
		<h2> Registration Form</h2>
		
		<form  action="Registration" method="post">
			<div class="input-field">
				<input type="text" name="name" value ="<%=lista.get(1) %>">
				<label><%=lista.get(0) %></label>
			</div>
			<div class="input-field">
			
				<input type="text" name="email" value ="<%=lista.get(3) %>">
				<label><%=lista.get(2) %></label>
			</div>
			<div class="input-field">
				<input type="text" name="phone" value ="<%=lista.get(5) %>">
				<label><%=lista.get(4) %></label>
			</div>
			<div class="input-field">
			
				<input type="text" name="uname" value ="<%=lista.get(7) %>">
				<label><%=lista.get(6) %></label>
			</div>	
			<div class="input-field">
				<input type="text" name="pass" value ="<%=lista.get(9) %>">
				<label><%=lista.get(8) %></label>
			</div>
				<input type="submit" name="act" value="Register" class="btn">
				<input type="submit" name="act" value="Cancel" class="btn1">
			</Form>
</body>
</html>