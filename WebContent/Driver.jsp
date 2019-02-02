<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.Date" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Driver</title>
<link rel="stylesheet" type="text/css" href="/AhmadiCIS4665/css/stylesheet.css">
</head>
<body bgcolor="blue" style="color:white">

<% if(session.getAttribute("usern")==null) {
		response.sendRedirect("login.jsp");
	}%>
	<% Date nowDate = new Date(); %>
	<div style="border: 1px solid white; width:100%; margin-left:0; margin-top:0; margin-bottom:2px; height:55px; background:#52a046">

<div class="switch" style="position:absolute; margin-left:35px; padding-bottom:0px">
<h5 style="margin-top:5px; position:absolute">${OnOfflinemsg}</h5>
	<Form action="ControllerRide" method="get" >
		<input type="submit" name="act" value="Offline">
		<input style="margin-top:29px" type="submit" name="act" value="Online">
	</Form>

</div>
<h3 style="margin-left:980px; padding-bottom: 10px"> <%=nowDate %></h3>
</div>
<div class="navbtns">


	<ul>
		<Form action="Forwarder" method="get">
			<li><input type="submit" class="button1" name="act" value="Home">
			<li><input type="submit" class="button2"name="act" value="Post Ride">
		<li><input type = "submit" class="button2" name="act" value="Last Week Cancelation">
		
			<li><input type="submit"  class="button3" name="act" value="Postings">
		</Form>
		<Form action="BookingStatus" method="post">
			<li><input type = "submit" class="button2" name="act" value="Available Pickups">
			<li><input type = "submit" class="button2" name="act" value="Booking Status">
			
		</Form>
			
		<Form action="BaseRateRide" method="post">
			<li><input type = "submit" class="button4" name="act" value="Base Rate Ride">
		</Form>
		<Form action="Forwarder" method="get">
			<li style="float:right"><input type="submit" class="button4" name="act" value="Signout">
		</Form>
	</ul>
</div>

<h3 style="color:red ; margin-left:220px">${pickupsNotAvail}</h3>
<h3 style="color: black ; margin-left:220px">${msg}</h3>
<h3 style="color:red ; margin-left:220px">${noOffer}</h3>
<marquee behavior="alternate" scrollamount="3"><p style="color:#52a046">${bookingStatus}</p></marquee>
</body>
</html>