<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ page import="java.util.Date" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Amazing Ride Sharing</title>
<link rel="stylesheet" type="text/css" href="/AhmadiCIS4665/css/stylesheet.css">
<Script src="/AhmadiCIS4665/css/myScript.js"></Script>
</head>
<body bgcolor="lightblue" style="color:white">

<% if(session.getAttribute("usern")==null) {
		response.sendRedirect("login.jsp");
	}%>
	<% Date nowDate = new Date(); %>
	<div style="border: 1px solid white; width:100%; margin-bottom:2px; height:55px; background:#52a046">
<h3 style="padding-bottom: 10px; position: absolute"> ${message}</h3> 
<h3 style="position:absolute">${userWelcome}</h3> 
<h3 style="margin-left:900px; padding-bottom: 10px"> <%=nowDate %></h3>
</div>
<Form action="Forwarder" method="post">

<ul>
	<li><input type="submit" class="button1" name="act" value="Drive">
	<li><input type="submit" class="button2" name="act" value="Ride">
	<li><input type="submit" class="button3"   name= "act" value="About">
	<li style="float:right"><input type="submit"  class="button4" name="act" value="Signout">
	</ul>

</Form>


<hr>
<h2>${noRide}</h2>
<marquee behavior="scroll" direction="right">
<img style="margin-top:110px" src="/AhmadiCIS4665/css/ars.png" width="70" height="70">
</marquee>
<marquee behavior="scroll" direction="right" scrollamount="20">
<img style="margin-top:100px" src="/AhmadiCIS4665/css/ars2.jpg" width="70" height="70">
</marquee>
<marquee behavior="scroll" direction="left">
<img style="margin-top:150px" src="/AhmadiCIS4665/css/ars.png" width="70" height="70">
</marquee>
</body>
</html>