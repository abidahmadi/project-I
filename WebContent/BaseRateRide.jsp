<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.Date" %>
 <%@page import = "model.BaseRateOffer" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- map below -->
<meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<title>Google Maps JavaScript API v3 Example: Reverse Geocoding</title>
<link href="http://code.google.com/apis/maps/documentation/javascript/examples/default.css" rel="stylesheet" type="text/css" />

  <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB_nfymeyQE52gSs26aBLTuxPwCsB1d6FE&callback=initialize"
  type="text/javascript"></script>
<!-- map up -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Driver</title>
<link rel="stylesheet" type="text/css" href="/AhmadiCIS4665/css/stylesheet.css">
</head>
<body  onLoad="initialize()" bgcolor="blue" style="color:black">

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
<!-------------- map below------------ -->
<div class="bottom" >
<script type="text/javascript">
 	var geocoder;
 	  var map;
 	  var infowindow = new google.maps.InfoWindow();
 	  var marker;
 	  function initialize() {
 	    geocoder = new google.maps.Geocoder();
 	   
 	  var latlng = new google.maps.LatLng(32.8140,96.9489);
 	    <!--var latlng = new google.maps.LatLng(38.730885,-94.997383);-->
 	    var myOptions = {
 	      zoom: 8,
 	      center: latlng,
 	      mapTypeId: 'roadmap'
 	    }
 	    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
 	    
 	    if (navigator.geolocation) {
 	        navigator.geolocation.getCurrentPosition(showPosition);
 	    } else { 
 	        x.innerHTML = "Geolocation is not supported by this browser.";
 	}

 	function showPosition(position) {
 	    document.getElementById('Latitude').value =  position.coords.latitude ; 
 	    document.getElementById('Longitude').value = position.coords.longitude;
 	    
 	}
 	  }

 	  function codeLatLng() {

 	    var lat = document.getElementById('Latitude').value;
 	    var lng = document.getElementById('Longitude').value;

 	    var latlng = new google.maps.LatLng(lat, lng);
 	    geocoder.geocode({'latLng': latlng}, function(results, status) {
 	      if (status == google.maps.GeocoderStatus.OK) {
 	        if (results[1]) {
 	          map.setZoom(11);
 	          marker = new google.maps.Marker({
 	              position: latlng, 
 	              map: map
 	            
 	          }); 
 	        var address= (results[1].formatted_address);
 	        document.getElementById('Address').value= results[1].formatted_address;
 	        infowindow.setContent(results[1].formatted_address);


 	          infowindow.open(map, marker);
 	        } else {
 	          alert("No results found");
 	        }
 	      } else {
 	        alert("Geocoder failed due to: " + status);
 	      }
 	    });
 	  }
 	</script>
 	  <input name="Latitude" type="hidden" id="Latitude"  />
 	  <input name="Longitude" type="hidden" id="Longitude"  />
 	  
 	  <input type="button" value="Current Location" onClick="codeLatLng()">
 	<div id="map_canvas" style="height: 300px; width: 70%; margin-left:180px">
 	</div>
 	<!---------- map above-------- -->
<div class="container">

	<div class="left">
	<%  if(m.getCurrentLoc().equals("none")){%>
		
 					<Form action ="BaseRateRide" method ="post">
 						<h5 class="onoffl">OFFLINE</h5>
 							<%if(m.getMaxDistance()==-1){
 							 %><h5 style="color:red; margin-left:20%">All fields are mandatory.</h5><%} else %>
 			 <% if(m.getMaxDistance()==-2){%><h5>Something went wrong. TRY AGAIN later.</h5><%} %>
	
 						<label>Current location</label><input type="text" id="Address" name = "pickupLoc" size="40"> <br>
 						<label>Max distance :</label><input type="text" name = "maxDistance" size="40"><br>
 						<input style="margin-left:135px" type="submit" name ="act" value = "Go Online">
 					</Form>
	<% } else%>
	 <% if(m.getCurrentLoc() !=null && m.getCurrentLoc()!=("none")){
		%>
			<div class="formLeft">
				<Form action = "BaseRateRide" method ="post">
					<h5 class="onoffl">ONLINE</h5>
					<p>${noRide}</p>
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
											<br>
												<p>Ride request from</p>
												<p><%=m.getRiderName()%></p><p> located in: <%=m.getCurrentLoc()%></p>
												<input type="hidden" name="riderId" value="<%=m.getRiderId()%>">
												
												<input type="hidden" name="current_Loc" value="<%=m.getCurrentLoc()%>">
											
												<input type="hidden" name="riderName" value="<%=m.getRiderName()%>">
												<br><br>
												 <input type="Submit" name="act" value="Accept Request">
											</Form>
										</div>
 	<%} else%>
 						<%if(m.getRideReq().equals("Accepted")){ %>
 						<div class="right">
											<Form action="BookingBaseRate" method="post"> <br>
												<p>You have accepted a ride</p>
												<p>Please go and pickup: </p>
												<p><%=m.getRiderName()%> from <%=m.getCurrentLoc()%></p>
												<input type="hidden" name="riderName" value="<%=m.getRiderName()%>">
												<input type="hidden" name="riderID" value="<%=m.getRiderId() %>">
												<input type="hidden" name="drvrID" value="<%=m.getDriverId()%>">
												<input type="hidden" name="currLoc" value="<%=m.getCurrentLoc()%>">
												

												<br><br><br>
												<input type="Submit" name="act" value="Decline">
											</Form>
										</div>
 	<%}%>					
 	<%}%>
	</div>
</div>
</body>
</html>