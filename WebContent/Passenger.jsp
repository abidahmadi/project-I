<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import = "model.Booking" %>
    <%@page import = "model.User" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ride Offer List</title>
<link rel="stylesheet" type="text/css" href="/AhmadiCIS4665/css/stylesheet.css">
<style>table{
margin-top:30px;}

table, th, td {
    border: 2px solid white;
    border-collapse: collapse;
    color: white;
    background-color: #52a046;
    padding: 10px;
    text-align: center;
}
table, th{
background-color: red;
    color: white;}
</style>
</head>
<body style="align:center" bgcolor="lightblue" style="color:white">


<div class="navbtns">


	<ul>
		<Form action="Forwarder" method="get">
			<li><input type="submit" class="button1" name="act" value="Home">
			<li><input type="submit" class="button2"name="act" value="Post Ride">
		
			<li><input type="submit"  class="button3" name="act" value="Postings">
		</Form>
		<Form action="BookingStatus" method="post">
			<li><input type = "submit" class="button2" name="act" value="Available Pickups">
			<li><input type = "submit" class="button2" name="act" value="Booking Status">
		</Form>
		<Form action="Forwarder" method="get">	
			<li style="float:right"><input type="submit" class="button4" name="act" value="Signout">
		</Form>
	</ul>


</div>

<hr>

	<%
	if(session.getAttribute("usern")==null) {
		response.sendRedirect("login.jsp");
		}
		%>
	<table align="center">
	
	
	<div style="border: 1px solid white; width:350px; margin-left:340px; margin-top:30px; height:85px; background:white; border-radius:8px">
	<p style="color:blue;padding:5px 10px 15px 23px">Available Pick-ups!. <br>The following people have booked ride with you.</p>
	
	</div>

	<th>Passenger No.</th><th>Full Name</th><th>Phone Number</th><th>Email Address</th>
	
	<%User[] search = (User[])request.getAttribute("pList"); %>
	<%	
		int lineNo = 1;
	 	String a = "Passenger ";
	 	for(int i = 0; i < search.length; i++){
		%>	
		

	<tr>
	<td><%=a +lineNo %>
	<td><%=search[i].getName() %>
	<td><%=search[i].getUserPhone() %>
	<td><%=search[i].getUserEmail()%>
	</td></tr>
	<% lineNo++;}
	%>
		</table>
		
</body>
</html>