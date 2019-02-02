<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.Date" %>
 <%@page import = "model.BaseRateOffer" %>
 <%@page import = "model.BookingBRate" %>
 <%@page import = "daoImpl.*" %>
 <%@page import = "dao.*" %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- map below -->
<meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<title>Google Maps JavaScript API v3 </title>
<link href="http://code.google.com/apis/maps/documentation/javascript/examples/default.css" rel="stylesheet" type="text/css" />
  <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB_nfymeyQE52gSs26aBLTuxPwCsB1d6FE&callback=initialize"
  type="text/javascript"></script>
<!-- map up -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ride Search</title>
<link rel="stylesheet" type="text/css" href="/AhmadiCIS4665/css/stylesheet.css">
</head>
<body onLoad="initialize()" bgcolor="blue" style="color:black">

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
			<li><input type="submit" class="button2"name="act" value="Flate Rate Ride">
		</Form>
		<Form action="Forwarder" method="get">	
			<li style="float:right"><input type="submit" class="button4" name="act" value="Signout">
		</Form>
	</ul>
</div>
	<!-- getting BaseRideOffer object -->
	<!-------------- map below------------ -->
<div class="bottom" >
<script type="text/javascript">
 	var geocoder;
 	  var map;
 	  var infowindow = new google.maps.InfoWindow();
 	  var marker;
 	  function initialize() {
 	    geocoder = new google.maps.Geocoder();
 	    var latlng = new google.maps.LatLng(38.730885,-94.997383);
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
	<Form  action = "BookingBaseRate" method ="post">
		<div class="onoffl"><h5>Get a ride with Us!</h5></div>
		<h5 style="color:red">${noRide}</h5>

		<label>Current  Location</label><input type="text" name ="pickup" id="Address" size="40"><br>
		<label>Enter Destination</label><input type="text" name ="destination" size="40"><br>
		<label>Approx.  Distance</label><input type="text" name ="distance" placeholder="Enter approximate distance" size="40"><br>
		<input type="submit" name ="act" value ="Search"> 
		<input  type="submit" name ="act" value ="Refresh">
		</Form>
 	</div>
 	<div class="right">
 	<%BaseRateOffer m =(BaseRateOffer)request.getAttribute("showRide"); %>
 	<% if (m !=null){%>
	 
		<div class="formRight" method="post">
		
		<Form action="BookingBaseRate" method="post">
	
		<input type="hidden" name ="drvrID" value="<%=m.getDriverId()%>">
		<input type="hidden" name="destination" value="<%=m.getDestination() %>">
		<input type="hidden" name="currentLoc" value="<%=m.getCurrentLoc() %>">
		<p>Offer is available from <%=m.getCurrentLoc() %></p>
		<p>to <%=m.getDestination() %></p>
		 <input type="Submit" name="act" value="Accept">
		 <input type="Submit" name="act" value="Cancel">
		 </Form>
		 	<Form action="Forwarder">
		<input type="Submit" name="act" value="Cancel">
		<%int driverId =m.getDriverId();
		 session.setAttribute("drvrID", driverId);%>
		 
		
		</Form>
		</div>
	
	<%} else%>
			<%String cc = (String)request.getAttribute("requestConf");%>
			
			
			<%if( cc!=null){
			%>
			<div class="formRight">
			
			<h3>Your request was sent to the first driver in the queue.You will receive a response within 60s.</h3>
			<Form action="BookingBaseRate" method="post">
			<input type="hidden" name ="drvrID" value="<%=session.getAttribute("drvrID")%>">
			<input type="Submit" name="act" value="Cancel">
		 	</Form>
		 </div>
		 
	<%} else %>
					<%BookingBRate g =(BookingBRate)request.getAttribute("showB"); %>
					<% if (g !=null){%>
					<div class="formRight">
								<%if(g.getStatus().equals("Waiting")){ %>
								<p><%=g.getStatus()%></p>
								<p>Your request was sent to the first driver in the queue.</p>
								<p>The driver will respond to your request within 60s.</p>
					</div>
								<%} else%>
								<%if(g.getStatus().equals("Accepted")){ %>
								
								 <%{ %> <p>The driver is on the way and coming to pick you up.</p>
								 </p> <%UserDaoInter k = new UserDaoImpl();%>
								<%String[] info = k.getUserById(g.getDriverId()); %>
								<p>Driver Name: <%=info[0] %></p>
								<p>Driver Email: <%=info[2] %></p>
								<p>Driver Phone: <%=info[1] %></p>
								 <%}%>
								 
				<%}}%>
			</div>
	
</div>
</body>
</html>