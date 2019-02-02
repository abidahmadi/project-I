

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

/**
 * Servlet implementation class FakeRegister
 */
@WebServlet("/FakeRegister")
public class FakeRegister extends HttpServlet {
	
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String error ="";
		Boolean complete = true;
		
		String name = request.getParameter("name");
		error = error + "name=" +name;
		if(name==null || name.trim().equals("")){
			complete =false;
		}
		
		String email = request.getParameter("email");
		error = error + "&email=" +email;
		if(email==null || email.trim().equals("")){
			complete =false;
		}
		
		String phone = request.getParameter("phone");
		error = error + "&email=" +email;
		if(phone==null || phone.trim().equals("")){
			complete =false;
		}
		
		String us = request.getParameter("us");
		error = error + "&email=" +us;
		if(us==null || us.trim().equals("")){
			complete =false;
		}
		
		String pass = request.getParameter("pass");
		error = error + "&email=" +pass;
		if(pass==null || pass.trim().equals("")){
			complete =false;
		}
		
	if(complete)	{
		
		User d = new User();
		d.setName(name);
		d.setUserEmail(email);
		d.setUserPhone(phone);
		d.setUserName(us);
		d.setPassword(pass);
		
		try{
			Class.forName("com.mysql.jdbc.Driver"); 
			Connection con = con = DriverManager.getConnection("jdbc:mysql://localhost:3306/FinalJava?useSSL=false", "root", "menu");
			String sql = "insert into User(Full_Name, Email_Address, Phone, Username, Password)values(?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, phone);
			ps.setString(4, us);
			ps.setString(5, pass);
			
			int i = ps.executeUpdate();
			
					
		}catch(Exception j){
			j.printStackTrace();
		}
	}
		
	response.setContentType("text/html");
	PrintWriter out = response.getWriter();
	
		out.println("<!DOCTYPE html>" +
					"<html><head></head><body>"+
				
		"<registration is complete"+
		"<a href = \"fakeLogin.jsp\">Login here </a>"+
		
		
		"</body></html>"
				
				
				
				);
		
		
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
