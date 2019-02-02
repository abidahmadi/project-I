<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/AhmadiCIS4665/css/stylesheet.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container1">
	<div class="left">
	<Form action = "BookingBaseRate" method ="post">
		<div class="onoffl"><h5>Get a ride with Us!</h5></div>
		<h5 style="color:red">${noRide}</h5>
		<h5></h5>
		<input type="text" name ="pickup" placeholder="Enter pickup location" size="20"><br>
		<input type="text" name ="destination" placeholder="Enter destination" size="20"><br>
		<input type="text" name ="distance" placeholder="Enter approximate distance" size="20"><br>
		<input type="submit" name ="act" value ="Search"> 
		<input  type="submit" name ="act" value ="Refresh">
		<!--  style="margin-left:120px"-->
 	</Form>
	</div>
</div>
</body>
</html>