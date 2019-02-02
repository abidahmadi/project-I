<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="model.Ride"%>
<%@page import="serviceUtility.Utilities"%>

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
    background-color: #52a046;s
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


<h3 style="color:red ; margin-left:140px">${updatemsgfromEditPosting}</h3>
<h3 style="color:red ; margin-left:140px">${messages}</h3>
<h3 style="color:red ; margin-left:140px">${failedupdatefrmEditPsoting}</h3>
<Form action="" method="post">
		
 <% 
 	if(session.getAttribute("usern")==null) {
		response.sendRedirect("login.jsp");
	}%>
						               
	<%--fetch user ID using user name from session --%>
	
	<table align="center">
	<tr>
	<th>No.</th><th>Ride_ID</th><th>User_ID</th><th>From</th><th>To</th><th>Date</th><th>Time</th><th>Fare</th><th>AvailSeat</th><th style="background-color: red; color:white">Booked Seat</th><th>Earnings</th><th>Action</th></tr>
	<!-- receiving data from controller -->		
 <%Ride[] p =(Ride[])request.getAttribute("posting"); %>
<%--check if there is any related record in table Ride. --%>
		<%int i = 0;%>
		<%int rowN =1; %>
		<%for(Ride a:p){ %>
<tr><td><%=rowN++ %>
<td><%=a.getRide_ID()%>
<td><%=a.getUser_ID() %>
<td><%=a.getFrom()  %>
<td><%=a.getTo()  %>
<td><%=a.getDate() %>
<td><%=a.getTime() %>
<td><%=a.getFare()%>
<td><%=a.getSeat() %>
<td style="color:red"><%=a.getSeatsBooked() %>
<td><%=a.getEarning() %>

<td><a href='EditRide.jsp?u=<%=p[i].getRide_ID() %>' >Edit</a>
<a href='DeleteRide.jsp?u=<%=p[i].getRide_ID() %>'>Delete</a>
			<%} %>
</td></tr>
				

	</table>
	</Form>
</body>
</html>
