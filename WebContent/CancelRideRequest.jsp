<%@page import="daoImpl.RideDaoImpl"%>
<%@page import="dao.RideDao"%>
<%@page import="model.Ride"%>
<%@page import="serviceUtility.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cancel Booking Request</title>
<link rel="stylesheet" type="text/css" href="/AhmadiCIS4665/css/stylesheet.css">
</head>
<body bgcolor="blue" style="color:white">

<% if(session.getAttribute("usern")==null) {
		response.sendRedirect("login.jsp");
	}%>
	
<div class="navbtns">


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


</div>

<div style="border: 1px solid white; width:50%; margin-left:0; margin-top:0; margin-bottom:2px; height:265px; padding-left:30px; background:#52a046">


  <h3> Cancellation Page</h3>
  <h3 style="color:red ; margin-left:220px">${msg}</h3>

 
  <div class="selectData">
  <Form action = "BookingContoller" method="post">
  <label>Seat Booked: </label><input type="text" name ="seatBookd" value=${sB} disabled><br>
  <label>Fare P Person: </label><input type="text" name ="farePPer" value=${fpp} disabled><br>
  <label>Fare Paid: </label><input type="text" name ="farePd" value=${fp} disabled><br>
  <input type="hidden" name ="drverID" value=${driverID}>
	<input type="hidden" name ="seatBooked" value=${sB}>
	<input type="hidden" name ="frm" value=${frm}>
	<input type="hidden" name ="to" value=${to}>
  <input type="hidden" name ="bookingID" value=${bID}>
  <input type="hidden" name ="rideID" value=${rID}><br>
  </label><input type="hidden" name ="farePPerson" value=${fpp}>
 </label><input type="hidden" name ="farePaid" value=${fp} >
 
<Label>How many seats do you want to cancel?</Label> <br>
<input  type="number" name="seatNumber" min="1" max=${sB}>

  	  		<br><br>
<input type="submit" name="act" value="Cancel Ride">

</Form>

</div>
<h1>${updatemsgfromEditPosting}</h1>
			
</body>
</html>