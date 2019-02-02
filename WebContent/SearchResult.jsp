<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import = "model.Ride" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ride Search Result</title>
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
	
<Form action="Forwarder" method="post">
	<ul>
		<li><input type="submit" class="button1" name="act" value="Home">
		<li><input type="submit" class="button2" name="act" value="Search Ride">
	
		<li><input type="submit" class="button3" name="act" value="My Booking">

		<li><input type="submit" class="button4" name="act" value="Signout">
	</ul>
</Form>

	<%
	if(session.getAttribute("usern")==null) {
		response.sendRedirect("login.jsp");
		}
		%>
		
	<%Ride[] search = (Ride[])request.getAttribute("searchList"); %>
	
	<table align="center">
	
	<div style="border: 1px solid white; width:350px; margin-left:340px; margin-top:30px; height:85px; background:white; border-radius:8px">
	<p style="color:blue;padding:10px 10px 15px 23px">Looks like there are a few rides available.</p>
	
	</div>
	<th>Number</th><th>Ride_ID</th><th>From</th><th>To</th><th>Date</th><th>Time</th><th>Available Seat</th><th>Fare</th><th>Action</th>
	
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
	<td><%=search[i].getSeat() %>
	<td><%=search[i].getFare() %>
	<td> <a href='BookingContoller?u=<%= search[i].getRide_ID()%>'>Select</a>
	
	</td></tr>
	
	<% lineNo++;}
	%>
		</table>
</body>
</html>