<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.Date" %>
 <%@page import = "model.BaseRateOffer" %>
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

<h3 style="margin-left:900px; padding-bottom: 10px"> <%=nowDate %></h3>
</div>
<div class="navbtns">

	<ul>
		<Form action="Forwarder" method="get">
			<li><input type="submit" class="button1" name="act" value="Home">
			
			<li><input type="submit"  class="button3" name="act" value="Drive">
		
			<li style="float:right"><input type="submit" class="button4" name="act" value="Signout">
		</Form>
	</ul>
</div>
	<!-- getting BaseRideOffer object -->
	<%BaseRateOffer m =(BaseRateOffer)request.getAttribute("listB"); %>

<div class="container">
	<div class="left">
	<%  if(m.getCurrentLoc().equals("none")){%>
		
 					<Form action ="BaseRateRide" method ="post">
 						<h5 class="onoffl">OFFLINE</h5>
 							<%if(m.getMaxDistance()==-1){
 							 %><h5 style="color:red; margin-left:20%">All fields are mandatory.</h5><%} else %>
 			 <% if(m.getMaxDistance()==-2){%><h5>Something went wrong. TRY AGAIN later.</h5><%} %>
	
 						<label>Current location</label><input type="text" name = "pickupLoc"> <br>
 						<label>Max distance</label><input type="text" name = "maxDistance"><br>
 						<input style="margin-left:135px" type="submit" name ="act" value = "Go Online">
 						<input style="margin-left:135px" type="submit" name ="act" value = "Get Current Location">
 	
 					</Form>
 				
	<% } else%>
	
	 <% if(m.getCurrentLoc() !=null && m.getCurrentLoc()!=("none")){
		%>
		
			<div class="formLeft">
				<Form action = "BaseRateRide" method ="post">
					<h5 class="onoffl">ONLINE</h5>
					<h5><%=m.getForMin()/60000%> mins</h5>
					<h5>Current Location: <%=m.getCurrentLoc() %></h5>
					<h5>Maximum distance prefer to travel: <%=m.getMaxDistance() %> mi</h5>
					<input type="submit" name ="act" value ="Go Offline"> 
					<input style="margin-left:2px" type="submit" name ="act" value ="Refresh">
 				</Form>
 		</div>
 		</div>	
 			 <% if(m.getRideReq().equals("OneRequest")){
													%>
												
									<div class="right">
											<Form action="BookingBaseRate" method="post"> 
												<p>Ride request from</p>
												<p><%=m.getRiderName()%></p>
												<input type="text" name="riderId" value="<%=m.getRiderId()%>">
												<input type="text" name="current_Loc" value="<%=m.getCurrentLoc()%>">
												<input type="text" name="riderName" value="<%=m.getRiderName()%>">
												
												 <input type="Submit" name="act" value="Accept Request">
												<input type="Submit" name="act" value="Decline">
											</Form>
										</div>
								
									
 	<%} else%>
 						<%if(m.getRideReq().equals("Accepted")){ %>
 						<div class="right">
											<Form action="" method="post"> 
												<p>You have accepted a ride</p>
												<p>Plz go pickup </p>
												<p><%=m.getRiderName()%> from <%=m.getCurrentLoc()%></p>
												
												<input type="Submit" name="act" value="Decline">
											</Form>
										</div>
 	<%}%>					
 	<%}%>
	</div>

</body>
</html>