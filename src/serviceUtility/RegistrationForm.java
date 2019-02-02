package serviceUtility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Registration-Form")
public class RegistrationForm extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// replace null with empty
		String name = Utilities.replaceNull(request.getParameter("name"));
	    // if value is empty ask to fill in value
		String namePrompt =
	      Utilities.prompt(request.getParameter("name"),
	             "Full Name:", 
	             "You must enter a Full Name:");
	    String email = Utilities.replaceNull(request.getParameter("email"));
	    String emailPrompt =
	    		Utilities.prompt(request.getParameter("email"),
	             "Email Address:",
	             "You must enter an Email Address:");
	    String phone = Utilities.replaceNull(request.getParameter("phone"));
	    String phonePrompt =
	    		Utilities.prompt(request.getParameter("phone"),
	             "Phone Number:",
	             "You must enter a Phone Number:");
	    
	    String uname = Utilities.replaceNull(request.getParameter("uname"));
	    String unamePrompt =
	    		Utilities.prompt(request.getParameter("uname"),
	             "Username:",
	             "You must enter a Username:");
	    
	    String pass = Utilities.replaceNull(request.getParameter("pass"));
	    String passPrompt =
	    		Utilities.prompt(request.getParameter("pass"),
	             "Password:",
	             "You must enter a Password:");
	    ArrayList <String> list = new ArrayList<String>();
	    	list.add(namePrompt);
	    	list.add(name);
	    	list.add(emailPrompt);
	    	list.add(email);
	    	list.add(phonePrompt);
	    	list.add(phone);
	    	list.add(unamePrompt);
	    	list.add(uname);
	    	list.add(passPrompt);
	    	list.add(pass);
	    	request.setAttribute("list", list);
	    	request.getRequestDispatcher("Registration2.jsp").forward(request, response);
	  
	}
}
