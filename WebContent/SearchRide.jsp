<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.*"%>
<%@page import="serviceUtility.Utilities"%>
<%@page import="dao.UserDaoInter"%>
<%@page import="model.Ride"%>
<%@page import="daoImpl.UserDaoImpl"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search Ride</title>
<link rel="stylesheet" type="text/css" href="/AhmadiCIS4665/css/stylesheet.css">
</head>

<body bgcolor="blue" style="color:white">
<fmt:formatDate value="${bean.calendar.time}" pattern="yyyy-MM-dd" />

<% if(session.getAttribute("usern")==null) {
		response.sendRedirect("login.jsp");
	}%> 
	
	<% 
		String username = session.getAttribute("usern").toString();
		UserDaoInter v = new UserDaoImpl();
		int userid = v.getUserID("username");
     %>

<Form action = "Forwarder">
<ul>
	<li><input type="submit" class="button2" name= "act" value="Home">
	</Form>
	<Form action="BookingStatus" method="post">
		<li><input type="submit" class="button3" name="act" value="My Booking">
		</Form>
		<Form action = "Forwarder">
	<li style="float:right"><input type="submit" class="button4" name= "act" value="Signout">
</ul>
</Form>
<hr>
<div style="border: 1px solid white; width:100%; margin-left:0; margin-top:30px; height:100px; background:#52a046; border-radius:2px">
<Form action = "ControllerRide" method="post">

<h3 style="color:white"><b>Tell us where would you like to go to.</h3>
  <h3 style="color:red ; margin-left:220px">${msg}</h3>
  <input type="hidden" name="userid" value="<%=v.getUserID(username)%>">
From <select name="from">
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
To <select name="to">
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


Date <input type="Date"  name="date">
<%-- Date2: <input type="date" name="bday">--%>

<input type="submit" name="act" value="Search">
</Form>
</div>
<hr>
<h1 style="color: red; margin-left:150px">${NoRideAvailmsg}</h1>
 
</body>
</html>