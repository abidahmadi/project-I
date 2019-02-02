package controller;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDaoInter;
import daoImpl.UserDaoImpl;
import model.User;
import serviceUtility.Utilities;

@WebServlet("/Login")
public class Login extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionDetect = request.getParameter("act");
if("Log In".equals(actionDetect)){	
		UserDaoInter cd = new UserDaoImpl();
		
		String username = request.getParameter("uname");
		String password = request.getParameter("pass");
		
		if (username==null || username.trim().equals("") || password ==null || password.trim().equals("")){
			request.setAttribute("msg", "Username or password cannot be empty.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}else{
		// step 2: pass data to the class model object	
		User d=cd.getUser(username, password);
		
		if(d!=null && d.getName()!=null){
			
			if(d.getStatus() == 2){///2 means account is active
			String uName = d.getUserName();
			int userID = d.getId();
			
			
			HttpSession session = request.getSession();
			String fname = d.getName();
			session.setAttribute("usern", uName);
			session.setAttribute("name", fname);
			session.setAttribute("uID", userID);
			
			request.setAttribute("message", "Welcome: "+ fname+ "!");
			
			request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
			} 
				{
					request.setAttribute("mssg", "Your account is not active. Check your email for activation code.");
					request.getRequestDispatcher("/AccountActivation.jsp").forward(request, response);
				}
		}	
		else{
			request.setAttribute("mssg", "Wrong usernam or password. Please try again.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	  }
}
else {
	String code = request.getParameter("actCode");
	Connection con = Utilities.connect();
	String qry = "Select * FROM User WHERE Activation_Code = '"+code+"'";
	String sql = "Update User SET Status = 2 WHERE Activation_Code = '"+code+"'";
		try{ PreparedStatement ps = con.prepareStatement(qry);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			try{
			PreparedStatement pss = con.prepareStatement(sql);
			pss.executeUpdate();
			request.setAttribute("mssg", "Your account has been activated.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			}catch(SQLException exc){exc.printStackTrace();}
			
		} else {
			request.setAttribute("mssg", "Activation code does not match. Try Again!");
			request.getRequestDispatcher("/AccountActivation.jsp").forward(request, response);
		}
		}catch(SQLException ex){ex.printStackTrace();}
		
}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}
