<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.*"%>
<%@page import="serviceUtility.Utilities"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/AhmadiCIS4665/css/stylesheet.css">
<title>Offer Ride</title>
</head>

<body>
<fmt:formatDate value="${bean.calendar.time}" pattern="yyyy-MM-dd" />

<% if(session.getAttribute("usern")==null) {
		response.sendRedirect("login.jsp");
	}%> 
	
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
<div style="border: 1px solid white; width:100%; margin-left:0; margin-top:30px; height:95px; background:white; border-radius:2px">
<Form action = "ControllerRide" method="post">

<h3> Please fill in the ride offer form below</h3>
  
From: <select name="from">
  	<optgroup label="Kansas"> 	
  	
  <option selected disabled hidden style='display: none' value=''></option>

  	<option value="Overland Park">Overland Park</option>
  	<option value="Lenexa">Lenexa</option>
  	<option value="Olathe">Olathe</option>
  	</optgroup>
<optgroup label="Missouri">
     <option value="Kansas City">Kansas City</option>
   	<option value="Warrensburg">Warrensburg</option>
  	<option value="St Louis">St Louis</option>
  	<option value="Independence">Independence</option>
  	<option value="Overland Park">Overland Park</option>
  	<option value="Lees Summit">Lees Summit</option>
  	</optgroup>
</select>
To: <select name="to">
	<option selected disabled hidden style='display: none' value=''></option>
	<optgroup label="Kansas"> 
  	<option value="Overland Park">Overland Park</option>
  	<option value="Lenexa">Lenexa</option>
  	<option value="Olathe">Olathe</option>
  	</optgroup>
<optgroup label="Missouri">
     <option value="Kansas City">Kansas City</option>
   	<option value="Warrensburg">Warrensburg</option>
  	<option value="St Louis">St Louis</option>
  	<option value="Independence">Independence</option>
  	<option value="Overland Park">Overland Park</option>
  	<option value="Lees Summit">Lees Summit</option>
  	</optgroup>
</select>
Date: <input type="Date" placeholder="yyyy-mm-dd" name="date">

Time: 
 <select name="time">
	<option selected disabled hidden style='display: none' value=''></option>
  	<option value="00:00">00:00</option>
   	<option value="01:00">01:00</option>
  	<option value="01:15">01:15</option>
  	<option value="01:30">01:30</option>
  	<option value="01:45">01:45</option>
  	<option value="02:00">02:15</option>
  	<option value="02:30">02:30</option>
  	<option value="02:45">02:45</option>
   	<option value="03:00">03:00</option>
  	<option value="03:15">03:15</option>
  	<option value="03:30">03:30</option>
  	<option value="03:45">03:45</option>
  	<option value="04:00">04:00</option>
  	<option value="04:15">04:15</option>
  	<option value="04:30">04:30</option>
  	<option value="04:45">04:45</option>
  	<option value="05:00">05:00</option>
  	<option value="05:15">05:15</option>
  	<option value="05:30">05:30</option>
  	<option value="05:45">05:45</option>
  	<option value="05:00">06:00</option>
  	<option value="06:15">06:15</option>
  	<option value="06:30">06:30</option>
  	<option value="06:45">06:45</option>
  	<option value="07:00">07:00</option>
  	<option value="07:15">07:15</option>
  	<option value="07:30">07:30</option>
  	<option value="07:45">07:45</option>
  	<option value="08:00">08:00</option>
  	<option value="09:15">08:15</option>
  	<option value="09:30">08:30</option>
  	<option value="09:45">08:45</option>
  	<option value="10:00">09:00</option>
  	<option value="10:15">09:15</option>
  	<option value="10:30">09:30</option>
  	<option value="10:45">09:45</option>
  	<option value="11:00">10:00</option>
  	<option value="11:15">10:15</option>
  	<option value="11:30">10:30</option>
  	<option value="11:45">10:45</option>
  	<option value="12:00">11:00</option>
  	<option value="12:15">11:15</option>
  	<option value="12:30">11:30</option>
  	<option value="12:45">11:45</option>
  	<option value="13:00">12:00</option>
  	<option value="13:15">12:15</option>
  	<option value="13:30">12:30</option>
  	<option value="13:45">12:45</option>
  	<option value="13:00">13:00</option>
  	<option value="13:15">13:15</option>
  	<option value="13:30">13:30</option>
  	<option value="13:45">13:45</option>
  	<option value="14:00">14:00</option>
  	<option value="14:15">14:15</option>
  	<option value="14:30">14:30</option>
  	<option value="14:45">14:45</option>
  	<option value="15:00">15:00</option>
  	<option value="15:15">15:15</option>
  	<option value="15:30">15:30</option>
  	<option value="15:45">15:45</option>
  	<option value="16:00">16:00</option>
  	<option value="16:15">16:15</option>
  	<option value="16:30">16:30</option>
  	<option value="16:45">16:45</option>
  	<option value="17:00">17:00</option>
  	<option value="17:15">17:15</option>
  	<option value="17:30">17:30</option>
  	<option value="17:45">17:45</option>
  	<option value="18:00">18:00</option>
  	<option value="18:15">18:15</option>
  	<option value="18:30">18:30</option>
  	<option value="18:45">18:45</option>
  	<option value="19:00">19:00</option>
  	<option value="19:15">19:15</option>
  	<option value="19:30">19:30</option>
  	<option value="19:45">19:45</option>
  	<option value="20:00">20:00</option>
  	<option value="20:15">20:15</option>
  	<option value="20:30">20:30</option>
  	<option value="20:45">20:45</option>
  	<option value="20:00">20:00</option>
  	<option value="20:15">21:15</option>
  	<option value="20:30">20:30</option>
  	<option value="20:45">20:45</option>
  	<option value="21:00">21:00</option>
  	<option value="21:15">21:15</option>
  	<option value="21:30">21:30</option>
  	<option value="21:45">21:45</option>
  	<option value="22:00">22:00</option>
  	<option value="22:15">22:15</option>
  	<option value="22:30">22:30</option>
  	<option value="22:45">22:45</option>
  	<option value="22:00">23:00</option>
  	<option value="23:15">23:15</option>
  	<option value="23:30">23:30</option>
  	<option value="23:45">23:45</option>
  	<option value="24:00">24:00</option>
  	
  	
</select>

Seat:  <select name="seat">

	<option selected disabled hidden style='display: none' value=''></option>
  	<option value="1">1</option>
   	<option value="2">2</option>
  	<option value="3">3</option>
  	<option value="4">4</option>
  	<option value="5">5</option>
  	<option value="6">6</option>
  	<option value="7">7</option>
  	
</select>
Fare Per Person: <input type="text" name="fare">
<input type="submit" name="act" value="Post">
</Form>
</div>
<hr>
<h1 style="color:red ; margin-left:220px">${msg}</h1>
<h1 style="color:red ; margin-left:150px">${PostSuccessfromRideControler}</h1>
<h1 style="color:red ; margin-left:150px">${postFailedFrmRideController}</h1>
</body>
</html>