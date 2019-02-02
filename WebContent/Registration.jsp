<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>Registration</title>
<link rel="stylesheet" type="text/css" href="/AhmadiCIS4665/css/login.css">
</head>
<body  id="bodyreg">

<div class="wrapper">
		<h2> Registration Form</h2>
		
		<form  action="Registration" method="post">
			<div class="input-field">
				<input type="text" name="name">
				<label>Full Name</label>
			</div>
			<div class="input-field">
			<h3 style="color:red">${duplicteEmail}</h3>
				<input type="text" name="email">
				<label>Email</label>
			</div>
			<div class="input-field">
				<input type="text" name="phone">
				<label>Phone Number</label>
			</div>
			<div class="input-field">
			<h3 style="color:red">${duplicateUsername}</h3>
				<input type="text" name="uname">
				<label>Username</label>
			</div>	
			<div class="input-field">
				<input type="text" name="pass">
				<label>Password</label>
			</div>
				<input type="submit" name="act" value="Register" class="btn">
				<input type="submit" name="act" value="Cancel" class="btn1">
			</Form>
</body>
</html>