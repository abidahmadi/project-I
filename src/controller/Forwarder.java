package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthSeparatorUI;

import serviceUtility.Utilities;


@WebServlet("/Forwarder")
public class Forwarder extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String fullName = session.getAttribute("name").toString();
		String username = session.getAttribute("usern").toString();
		
		String userId = session.getAttribute("uID").toString();
		int id = Integer.parseInt(userId);
		
		String actionRecognizer = request.getParameter("act");
		
		if("Drive".equals(actionRecognizer)){
			
				SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
				String date = dtFormat.format(new Date());
				// checks if there is any booking request
				String qry = "Select count(*) as rowNo FROM Booking WHERE Driver_ID='"+id+"' and (Status='OnTime' or Status = 'Delayed') and Ride_Date >='"+date+"'";
				int rowCount = Utilities.countRowNo(qry);
			
				//checks if user is online or offline
				String qrySt = "Select count(*) as rowNo FROM Ride WHERE Driver_ID='"+id+"' and Date >='"+date+"' and Status='OnLine'";
				
				int onOfline = Utilities.countRowNo(qrySt);//onOfline value = 0 means user is offline or has no postings
				System.out.println(onOfline);
					if(rowCount==0){
						if(onOfline == 0){
				
							request.setAttribute("OnOfflinemsg", "OFFLINE" );
							request.getRequestDispatcher("Driver.jsp").forward(request, response);
					}else{
							request.setAttribute("OnOfflinemsg", "ONLINE" );
							request.getRequestDispatcher("Driver.jsp").forward(request, response);
					}
		
				}else{
						if(onOfline == 0){
							request.setAttribute("OnOfflinemsg", "OFFLINE" );
							request.setAttribute("bookingStatus", "You have "+ rowCount+ " booking request. Check Available Pickups.");
							request.getRequestDispatcher("Driver.jsp").forward(request, response);
						} else
							{request.setAttribute("OnOfflinemsg", "ONLINE" );
							request.setAttribute("bookingStatus", "You have "+ rowCount+ " booking request. Check Available Pickups.");
							request.getRequestDispatcher("Driver.jsp").forward(request, response);
				}
		   }
		}
		
		if("Ride".equals(actionRecognizer) || "Flate Rate Ride".equals(actionRecognizer)){
			
			request.setAttribute("userWelcome", "Hi "+session.getAttribute("name").toString());
			request.getRequestDispatcher("Rider.jsp").forward(request, response);
		}
if("Last Week Cancelation".equals(actionRecognizer) ){
			
	response.sendRedirect("CancelationReport.jsp");
		}
		if("Postings".equals(actionRecognizer)){
			response.sendRedirect("Postings.jsp");
		}
	
		else if("Home".equals(actionRecognizer)){
			request.setAttribute("userWelcome", "Welcome " +fullName +"!" );
			request.getRequestDispatcher("HomePage.jsp").forward(request, response);
		}                              
		else if("Signout".equals(actionRecognizer)){
			session.removeAttribute("usern");
			session.invalidate();
			response.sendRedirect("login.jsp");
		}    
		else if("Post Ride".equals(actionRecognizer)){
			response.sendRedirect("Ride.jsp");
		}	   
		
		else if ("Search Ride".equals(actionRecognizer)){
			response.sendRedirect("SearchRide.jsp");
		}
		
		else if ("Base Rate Ride".equals(actionRecognizer)){
			response.sendRedirect("SearchBROffer.jsp");
		}
		else if("Cancel".equals(actionRecognizer)){
			response.sendRedirect("SearchBROffer.jsp");
		}
		
	}           
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}
