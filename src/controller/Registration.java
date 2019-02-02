package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDaoInter;
import model.User;
import serviceUtility.*;
import daoImpl.*;


@WebServlet("/Registration")
public class Registration extends HttpServlet {
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get button value
		String actionDetect = request.getParameter("act");
		
	if("Cancel".equals(actionDetect)){
			response.sendRedirect("login.jsp");
	}else if
			("Register".equals(actionDetect)){
		
				String errorData = "";
				boolean isComplete = true;
				String name = request.getParameter("name");
				errorData = errorData + "name=" + name;
				// returns true if name is null or empty
			if(isMissing(name)) {
				isComplete = false;
				}
			
				String emailAd = request.getParameter("email");
				errorData = errorData + "&email=" + emailAd;
			if(isMissing(emailAd)) {
					isComplete = false;
				}
			
				String phoneN = request.getParameter("phone");
				errorData = errorData + "&phone=" + phoneN ;
				if(isMissing(phoneN)) {
				isComplete = false;
				}
				
				String usern = request.getParameter("uname");
				errorData = errorData + "&uname=" + usern;
				if(isMissing(usern)) {
					isComplete = false;
				}
				
				String pass = request.getParameter("pass");
				errorData = errorData + "&pass=" + pass;
				if(isMissing(pass)) {
				isComplete = false;
				}
				
if (isComplete) { // No missing form data
		
			
			//2.pass data to the class model object
			User m = new User();
			m.setName(name);
			m.setUserEmail(emailAd);
			m.setUserPhone(phoneN);
			m.setUserName(usern);
			m.setPassword(pass);
		
		//3. call method in DAO class to insert data in a table
		UserDaoInter d = new UserDaoImpl();
				//check duplicate email address
				boolean duplUserNname = d.checkDuplUsername(usern);
				//check duplicate email address
				boolean duplEmail = d.checkDuplEmail(emailAd);
			
			if(duplUserNname){
				 if(duplEmail){
					request.setAttribute("duplicteEmail", "This email is already in use.");
					request.setAttribute("duplicateUsername", "This username is taken.");
					request.getRequestDispatcher("Registration.jsp").forward(request, response);
				 }else {
				 	request.setAttribute("duplicateUsername", "This username is taken.");
					request.getRequestDispatcher("Registration.jsp").forward(request, response);
					}
				 
			}else if(duplEmail){
					request.setAttribute("duplicteEmail", "This email is already in use.");
					request.getRequestDispatcher("Registration.jsp").forward(request, response);
			// if no duplicate username or email then do the following		
			} else {
			
			//add user in user table
			int i = d.addUser(m);
			
			//generate activation code and send varification email
			int userId = d.getUserID(usern);
			String activationCode = userId + usern;
			//save activation code into user table
			Connection con = Utilities.connect();
			try{
				String qry ="Update User SET Activation_Code = '"+activationCode+"' WHERE User_ID ='"+userId+"'";
				PreparedStatement ps = con.prepareStatement(qry);
				ps.executeUpdate();
			}catch(SQLException e){e.printStackTrace();}
			
			String to = emailAd;
			
			String sub = "Activation";
			String activationC = "Activate your accout by using the following activation code: " + activationCode + 
					"              Use this link to activate your account    http://localhost:5437/AhmadiCIS4665/AccountActivation.jsp";
			
			
			SendEmail.sendEmailNow(to, sub, activationC);
			if(i!=0){
				
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				String title = "Registered successfully";
				String docType = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
						"Transitional//EN\">\n";
				out.println( docType
						+ "<html><head><title>"+title+
						"</title></head><body bgcolor=\"##FDF536\" align=\"center\"><h1>Activation code has been emailed to you.</h1>" +
						"<h1>Check your email for activation code</h1>" +
						" Click here to <a href=\"AccountActivation.jsp\">Activate your Account Now!</a>" 
						+
						"</body></html>"
						);
			}else{
				response.sendRedirect("Registration.html");
				}
			}
			// Missing form data
} else { 
			response.sendRedirect("Registration-Form" + "?" + errorData);
	      }
		}
	}
	private boolean isMissing(String param) {
	    return((param == null) || (param.trim().equals("")));
	  }
}
