<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import = "model.Ride" %>
    
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


	<%
	if(session.getAttribute("usern")==null) {
		response.sendRedirect("login.jsp");
		}
		%>
		
	<%Ride[] search = (Ride[])request.getAttribute("list"); %>
	
	<table align="center">
	<tr>
	<th>Number</th><th>Ride_ID</th><th>From</th><th>To</th><th>Date</th><th>Time</th><th>Fare/Person</th><th>Status</th></tr>
	<%	
		int lineNo = 1;
		for(int i = 0; i < search.length; i++){
		%>	
	<tr><td><%=lineNo %>
	<td><%=search[i].getRide_ID() %>
	<td><%=search[i].getFrom() %>
	<td><%=search[i].getTo() %>
	<td><%=search[i].getDate()%>
	<td><%=search[i].getTime() %>
	<td><%=search[i].getFare()%>
	<td><%=search[i].getStatus() %>
	
	
	</td></tr>
	
	<% lineNo++;}
	
	%>
		</table>
</body>
</html>