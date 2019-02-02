

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FakeLogin
 */
@WebServlet("/FakeLogin")
public class FakeLogin extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String un = request.getParameter("un");
		String pass = request.getParameter("pass");
		
		if(un.equals(null) || un.trim().equals("") || pass.equals(null) || pass.trim().equals("")){
			request.setAttribute("msg", " no empty fields plz");
			request.getRequestDispatcher("/fakeLogin.jsp").forward(request, response);
		} else { 
			
			try{
				Class.forName("com.mysql.jdbc.Driver"); 
				Connection con = con = DriverManager.getConnection("jdbc:mysql://localhost:3306/FinalJava?useSSL=false", "root", "menu");
				String qry = "Select * From User WHERE Username=? and Password=?";
				PreparedStatement ps = con.prepareStatement(qry);
				ps.setString(1,un);
				ps.setString(2, pass);
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()){
					HttpSession  session = request.getSession();
					session.setAttribute("usern", un);
					response.sendRedirect("/HomePage.jsp");
				}
				else {
					request.setAttribute("msg", "user not found");
					request.getRequestDispatcher("fakeLogin.jsp").forward(request, response);
				}
				
			}catch (Exception b){b.printStackTrace();}
			
			
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
