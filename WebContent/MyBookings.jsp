<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import = "model.Booking" %>
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



	<%
	if(session.getAttribute("usern")==null) {
		response.sendRedirect("login.jsp");
		}
		%>
		
	<%Booking[] search = (Booking[])request.getAttribute("list"); %>
	
	<table align="center">
	<tr>
	<th>Number</th><th style="display:none">BookingID</th><th>Ride_ID</th><th>From</th><th>To</th><th>Date</th><th>Time</th><th>FarePP<th>TotalPaid</th><th>NOF Seat Booked</th><th>Status</th><th>Action</th></tr>
	<!--   -->
	<%	
		int lineNo = 1;
		for(int i = 0; i < search.length; i++){
		%>	
		
	<Form action="BookingContoller" method="post">
	<tr><td><%=lineNo %>
	<!-- pass booking id to servlet with the below Cancel button -->
	<input type="hidden" name="seatBooked" value="<%=search[i].getBookdSeat()%>">
	<input type="hidden" name="farepp" value="<%=search[i].getTotalFare()%>">
	<input type="hidden" name="farep" value="<%=search[i].getFare()%>">
	<input type="hidden" name="rideID" value="<%=search[i].getRideID()%>">
	<input type="hidden" name="driverID" value="<%=search[i].getDriver_Id()%>">
	<input type="hidden" name="frm" value="<%=search[i].getFrom()%>">
	<input type="hidden" name="to" value="<%=search[i].getTo()%>">
	<td style="display:none"><input type="text" name="BkingID" value="<%=search[i].getBkingID()%>">
	<td><%=search[i].getRideID() %>
	<td><%=search[i].getFrom() %>
	<td><%=search[i].getTo() %>
	<td><%=search[i].getDate()%>
	<td><%=search[i].getTime() %>
	<td><%=search[i].getFare()%>
	<td><%=search[i].getTotalFare() %>
	<td><%=search[i].getBookdSeat() %>
	<td><%=search[i].getStatus() %>
	<td><input type ="Submit" name="act" value="Cancel"/></Form>

	</td></tr>
	
	<% lineNo++;
	}
	
	%>
		</table>
</body>
</html>