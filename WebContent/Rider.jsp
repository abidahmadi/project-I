<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.Date" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Looking For a Ride?</title>
<link rel="stylesheet" type="text/css" href="/AhmadiCIS4665/css/stylesheet.css">
</head>
<body bgcolor="blue" style="color:white">

<% if(session.getAttribute("usern")==null) {
		response.sendRedirect("login.jsp");
	}%>
<% Date nowDate = new Date(); %>
	<div style="border: 1px solid white; width:100%; margin-left:0; margin-top:0; margin-bottom:2px; height:55px; background:#52a046">
<h3 style="position:absolute">${userWelcome}</h3> 

<h3 style="margin-left:900px; padding-bottom: 10px"> <%=nowDate %></h3>
</div>

<Form action="Forwarder" method="post">
	<ul>
		<li><input type="submit" class="button1" name="act" value="Home">
		<li><input type="submit" class="button2" name="act" value="Search Ride">
		</Form>
		<Form action="BookingStatus" method="post">
		<li><input type="submit" class="button3" name="act" value="My Booking">
		</Form>
			<Form action="BookingBaseRate" method="post">
			<li><input type = "submit" class="button4" name="act" value="Base Rate Ride">
			</Form>
		<Form action="Forwarder" method="post">
		<li style="float:right"><input type="submit" class="button4" name="act" value="Signout">
	</ul>
</Form>
<hr>
<h3 style="color:black">${bookingCancelled}</h3>
<h3 style="color:black">${cancelFailed}</h3>
</body>
</html>