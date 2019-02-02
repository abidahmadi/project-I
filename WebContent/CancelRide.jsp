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
<title>Driver</title>
<link rel="stylesheet" type="text/css" href="/AhmadiCIS4665/css/stylesheet.css">
</head>
<body bgcolor="blue" style="color:white">

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

<div style="border: 1px solid white; width:50%; margin-left:0; margin-top:0; margin-bottom:2px; height:265px; padding-left:30px; background:#52a046">
<Form action = "BookingContoller" method="post">

  <h3> Cancel ride below.</h3>
  <h3 style="color:red ; margin-left:220px">${msg}</h3>

  <%
  	int num = Utilities.getIntParameter(request, "u", -1);
  	RideDao d = new RideDaoImpl();
  	Ride c = d.getPostingByRideID(num);
  %>
<div class="selectData">
   <input type="hidden" name="rideId" value="<%=c.getRide_ID()%>">
	
 <br>
 <Label>From</Label> <input  type="text" name="frm" value="<%=c.getFrom() %>" disabled><br>
 <Label>To</Label> <input  type="text" name="to" value="<%=c.getTo() %>" disabled><br>
<Label>Date</Label> <input  type="Date" name="date" value="<%=c.getDate()%>" disabled><br>
<Label>Time</Label> <input type ="text" value = "<%=c.getTime()%>" disabled> <hr>
  	 			

<Label>Cancellation Reason</Label> <input type ="text" name ="reason"> <hr>
  	  
 
<input type="submit" name="act" value="Cancel Ride">
			
		
</Form>





			
</body>
</html>