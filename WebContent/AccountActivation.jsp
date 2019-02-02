<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="/AhmadiCIS4665/css/login.css">

</head>
     
<body>
	<div class ="title"><h1> Activate Your Account Now </h1></div>
	<div class ="container">
	<h4 style="color:red ; margin-left:120px">${msg}</h3>
	<h4 style="color:purple ; margin-left:120px">${mssg}</h3>
			<div class ="left"></div>
				<div class ="right"> 
					<div class="formBox"> 
						<Form action="Login" method="post">
							<p>Activation Code</p>
							<input type="text" name="actCode"> 
							<input type="submit" name = "act" value ="Active Now">
							<a href="Registration.jsp"> Register</a><br><br>
					</Form>
				</div>
	</div>
	
</body>
</html>
